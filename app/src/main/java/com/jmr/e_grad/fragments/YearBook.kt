package com.jmr.e_grad.fragments

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.YearBookRelatedData
import com.jmr.e_grad.helper.linkHelper
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.recycleview.adapter.ImageAdapter
import com.jmr.e_grad.recycleview.data.ImageItem
import com.jmr.e_grad.recycleview.data.mediaItem
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils


class YearBook(private var mainActivity: MainActivity) : Fragment() {
    private lateinit var vpCoverPhotos: ViewPager2
    private lateinit var lnDots: LinearLayout
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }

    private val apiServices = apiServices()
    private val utils = utils()
    private val gson = Gson()
    private val arrCoverPhotos = ArrayList<ImageItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val yearBookView: View = inflater.inflate(R.layout.fragment_year_book, container, false)

        yearBookView.apply {
            vpCoverPhotos = findViewById(R.id.vpCoverPhotos)
            lnDots = findViewById(R.id.lnDots)
        }

        loadCovers()

        return yearBookView
    }

    fun loadCovers() {
        if (utils.hasInternet(requireContext())) {
            val yearBookRelatedData = YearBookRelatedData(
                mode = "get_media",
                schoolYear = getInt("yearGraduated")
            )

            mainActivity.allowSwitching = false
            arrCoverPhotos.clear()

            apiServices.getMedia(yearBookRelatedData) { it ->
                if (it!!.success) {
                    val mediaResponse = it.data

                    if (mediaResponse!!.media!!.isNotEmpty()) {
                        mediaResponse?.media?.let { mediaDetails ->
                            mediaDetails.forEach { currentMedia ->
                                currentMedia.apply {
                                    if (type == "pic") {
                                        if (isCoverPhoto == 1) {
                                            val link = linkHelper.eventPhotoLink + "${schoolYear}/${fileName}"

                                            arrCoverPhotos.add(
                                                ImageItem(
                                                    fileName,
                                                    link
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        if (arrCoverPhotos.isNotEmpty()) {
                            val imageAdapter = ImageAdapter()
                            val dotsImage = Array(arrCoverPhotos.size) {
                                ImageView(requireContext())
                            }

                            vpCoverPhotos.adapter = imageAdapter
                            imageAdapter.submitList(arrCoverPhotos)

                            dotsImage.forEach {
                                it.setImageResource(R.drawable.icn_circle_unselected)
                                lnDots.addView(it,params)
                            }
                            dotsImage[0].setImageResource(R.drawable.icn_circle_selected)

                            pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    dotsImage.mapIndexed { index, imageView ->
                                        if (position == index) {
                                            imageView.setImageResource(R.drawable.icn_circle_selected)
                                        } else {
                                            imageView.setImageResource(R.drawable.icn_circle_unselected)
                                        }
                                    }
                                    super.onPageSelected(position)
                                }
                            }

                            vpCoverPhotos.registerOnPageChangeCallback(pageChangeListener)
                        } else {
                            utils.showToastMessage(requireContext(),"No cover photo(s) uploaded")
                        }
                    }
                }

                mainActivity.allowSwitching = true
            }
        }
    }
}