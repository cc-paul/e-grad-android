package com.jmr.e_grad

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmr.e_grad.Constants.Companion.LOGIN_FRAGMENT
import com.jmr.e_grad.Constants.Companion.REGISTRATION_FRAGMENT
import com.jmr.e_grad.fragments.Login
import com.jmr.e_grad.fragments.Registration

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginFragMan : FragmentManager = supportFragmentManager
        val loginFragTrans : FragmentTransaction = loginFragMan.beginTransaction()
        val loginFrag = Login()

        loginFragTrans.apply {
            add(R.id.frLogin,loginFrag,LOGIN_FRAGMENT)
            commit()
        }
    }
}

class Constants {
    companion object {
        const val REGISTRATION_FRAGMENT = "RegistrationFragment"
        const val LOGIN_FRAGMENT = "LoginFragment"
        const val BOTTOM_DIALOG = "ModalBottomSheet"
        const val YEARBOOK_FRAGMENT = "YearbookFragment"
        const val ACCOUNT_FRAGMENT = "AccountFragment"
    }
}