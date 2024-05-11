package com.jmr.e_grad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.jmr.e_grad.fragments.Account
import com.jmr.e_grad.fragments.Login
import com.jmr.e_grad.fragments.YearBook
import java.time.Year

class MainActivity : AppCompatActivity() {
    private lateinit var lnYearBook: LinearLayout
    private lateinit var lnAccount: LinearLayout
    private lateinit var vYearBook: View
    private lateinit var vAccount: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lnYearBook = findViewById(R.id.lnYearBook)
        lnAccount = findViewById(R.id.lnAccount)
        vYearBook = findViewById(R.id.vYearBook)
        vAccount = findViewById(R.id.vAccount)

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

        loadYearBook()
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