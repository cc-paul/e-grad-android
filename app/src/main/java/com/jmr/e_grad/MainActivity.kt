package com.jmr.e_grad

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.jmr.e_grad.Constants.Companion.BOTTOM_DIALOG
import com.jmr.e_grad.data.YearBookRelatedData
import com.jmr.e_grad.fragments.Account
import com.jmr.e_grad.fragments.Awardee
import com.jmr.e_grad.fragments.BottomAchievement
import com.jmr.e_grad.fragments.BottomChangePassword
import com.jmr.e_grad.fragments.Media
import com.jmr.e_grad.fragments.YearBook
import com.jmr.e_grad.fragments.YearBookDetails
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.recycleview.adapter.gradsAdapter
import com.jmr.e_grad.recycleview.data.achievementPassItem
import com.jmr.e_grad.recycleview.data.getGradItem
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils
import java.io.File


class MainActivity : AppCompatActivity() {
    var allowSwitching = true

    private val utils = utils()
    private val apiServices = apiServices()
    private val gson = Gson()

    private var currentDownloadId: Long = -1
    private var downloadHandler: Handler? = null
    private var downloadReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomMenu)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            if (allowSwitching) {
                when (menuItem.itemId) {
                    R.id.navHome -> {
                        replaceFragment(Media(mainActivity = this))
                        true
                    }
                    R.id.navAwardee -> {
                        replaceFragment(Awardee(mainActivity = this))
                        true
                    }
                    R.id.navYearBook -> {
                        replaceFragment(YearBookDetails(mainActivity = this))
                        true
                    }
                    R.id.navCover -> {
                        replaceFragment(YearBook())
                        true
                    }
                    R.id.navProfile -> {
                        replaceFragment(Account())
                        true
                    }
                    else -> false
                }
            } else {
                false
            }
        }

        bottomNavigationView.selectedItemId = R.id.navCover
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.frMain)

        if (currentFragment?.javaClass != fragment.javaClass) {
            fragmentManager.beginTransaction()
                .replace(R.id.frMain, fragment)
                .commit()
        }
    }

    fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm: DownloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request: DownloadManager.Request = DownloadManager.Request(downloadUri)

            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )

            // Enqueue the new download
            currentDownloadId = dm.enqueue(request)

            // Show the progress bar
            utils.showProgress(this)

            // Ensure previous handler is stopped
            downloadHandler?.removeCallbacksAndMessages(null)

            // Track the download progress with a new Handler
            downloadHandler = Handler().apply {
                postDelayed(object : Runnable {
                    @SuppressLint("Range")
                    override fun run() {
                        val query = DownloadManager.Query().setFilterById(currentDownloadId)
                        val cursor: Cursor = dm.query(query)
                        if (cursor.moveToFirst()) {
                            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))

                            when (status) {
                                DownloadManager.STATUS_RUNNING -> {
                                    // Still running, keep checking
                                }
                                DownloadManager.STATUS_SUCCESSFUL -> {
                                    utils.closeProgress()
                                    removeCallbacksAndMessages(null)
                                }
                                DownloadManager.STATUS_FAILED -> {
                                    utils.closeProgress()
                                    utils.showToastMessage(this@MainActivity, "Image download failed")
                                    removeCallbacksAndMessages(null)
                                }
                            }
                        }
                        cursor.close()
                        postDelayed(this, 1000) // Continue checking every second
                    }
                }, 1000)
            }

            // Unregister previous receiver if necessary
            downloadReceiver?.let {
                unregisterReceiver(it)
            }

            // Listen for download completion with a new BroadcastReceiver
            downloadReceiver = object : BroadcastReceiver() {
                override fun onReceive(ctxt: Context, intent: Intent) {
                    val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                    if (id == currentDownloadId) {
                        utils.closeProgress()
                        utils.showToastMessage(ctxt, "Image download complete")
                    }
                }
            }.also {
                registerReceiver(it, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            }

        } catch (e: Exception) {
            utils.showToastMessage(this, "Image download failed")
        }
    }

    fun getAchievement(achievementPassItem: ArrayList<achievementPassItem>) {
        if (utils.hasInternet(this)) {
            utils.showProgress(this)

            val yearBookRelatedData = YearBookRelatedData(
                mode = "get_achievement",
                studentNumber = achievementPassItem[0].studentNumber
            )

            apiServices.getAchievement(yearBookRelatedData) {
                if (it!!.success) {
                    val achievementResponse = it.data
                    val achievement = achievementResponse.achievement
                    val bottomAchievement = BottomAchievement(achievement,achievementPassItem)

                    utils.closeProgress()
                    bottomAchievement.show(this.supportFragmentManager, BOTTOM_DIALOG)
                }
            }
        } else {
            utils.showToastMessage(this,"Unable to load data. Please connect to the internet")
        }
    }
}