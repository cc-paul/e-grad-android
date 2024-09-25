package com.jmr.e_grad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.jmr.e_grad.fragments.Account
import com.jmr.e_grad.fragments.Awardee
import com.jmr.e_grad.fragments.Login
import com.jmr.e_grad.fragments.Media
import com.jmr.e_grad.fragments.YearBook
import com.jmr.e_grad.fragments.YearBookDetails
import com.jmr.e_grad.helper.sharedHelper
import java.time.Year

class MainActivity : AppCompatActivity() {
    var allowSwitching = true

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


        if (savedInstanceState == null) {
            replaceFragment(Media(mainActivity = this))
        }
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
}