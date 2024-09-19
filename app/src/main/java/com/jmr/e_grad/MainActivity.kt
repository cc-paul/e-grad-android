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
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationView
import com.jmr.e_grad.fragments.Account
import com.jmr.e_grad.fragments.Login
import com.jmr.e_grad.fragments.YearBook
import com.jmr.e_grad.helper.sharedHelper
import java.time.Year

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {
    private lateinit var lnYearBook: LinearLayout
    private lateinit var lnAccount: LinearLayout
    private lateinit var vYearBook: View
    private lateinit var vAccount: View
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var imgMenu : ImageView
    private lateinit var nav_tvFullName: TextView
    private lateinit var nav_tvCourse: TextView
    private lateinit var nav_tvStudentNumber: TextView
    private lateinit var nav_tvSchoolYear: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lnYearBook = findViewById(R.id.lnYearBook)
        lnAccount = findViewById(R.id.lnAccount)
        vYearBook = findViewById(R.id.vYearBook)
        vAccount = findViewById(R.id.vAccount)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        imgMenu = findViewById(R.id.imgMenu)

        navigationView.apply {
            val headerView = getHeaderView(0)

            headerView.apply {
                nav_tvFullName = findViewById(R.id.tvFullName)
                nav_tvCourse = findViewById(R.id.tvCourse)
                nav_tvStudentNumber = findViewById(R.id.tvStudentNumber)
                nav_tvSchoolYear = findViewById(R.id.tvSchoolYear)
            }
        }

        navigationView.setNavigationItemSelectedListener(this)
        navigationView.setCheckedItem(R.id.nav_yearbook)

        lnYearBook.setOnClickListener {
            vYearBook.visibility = View.VISIBLE
            vAccount.visibility = View.INVISIBLE

            loadYearBook()
        }

        lnAccount.setOnClickListener {
            vYearBook.visibility = View.INVISIBLE
            vAccount.visibility = View.VISIBLE

            loadAccount()
        }

        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }


        loadDetails()
        loadYearBook()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_yearbook -> supportFragmentManager.beginTransaction()
                .replace(R.id.frMain, YearBook()).commit()
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.frMain, Account()).commit()
            R.id.nav_logout -> finish()
        }
        drawerLayout.closeDrawer(GravityCompat.END)

        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun loadDetails() {
        sharedHelper.apply {
            nav_tvFullName.text = getString("lastName") + ", " + getString("firstName") + " " + getString("middleName")
            nav_tvCourse.text = getString("course")
            nav_tvStudentNumber.text = getString("studentNumber")
            nav_tvSchoolYear.text = getInt("yearGraduated").toString()
        }
    }

    fun loadYearBook() {
        val yearBookFragMan : FragmentManager = supportFragmentManager
        val yearBookFragTrans : FragmentTransaction = yearBookFragMan.beginTransaction()
        val yearBookFrag = YearBook()

        yearBookFragTrans.apply {
            replace(R.id.frMain,yearBookFrag, Constants.YEARBOOK_FRAGMENT)
            commit()
        }
    }

    fun loadAccount() {
        val accountFragMan : FragmentManager = supportFragmentManager
        val accountFragTrans : FragmentTransaction = accountFragMan.beginTransaction()
        val accountBookFrag = Account()

        accountFragTrans.apply {
            replace(R.id.frMain,accountBookFrag, Constants.ACCOUNT_FRAGMENT)
            commit()
        }
    }

}