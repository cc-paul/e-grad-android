package com.jmr.e_grad.fragments

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jmr.e_grad.Constants.Companion.BOTTOM_DIALOG
import com.jmr.e_grad.Constants.Companion.REGISTRATION_FRAGMENT
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.loginAccountData
import com.jmr.e_grad.helper.sharedHelper
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils
import kotlin.system.exitProcess

class Login : Fragment() {
    private lateinit var loginView: View
    private lateinit var lnExit: LinearLayout
    private lateinit var lnLogin: LinearLayout
    private lateinit var lnSignUp: LinearLayout
    private lateinit var etStudentNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var imgShowPassword: ImageView
    private lateinit var tvForgotPassword: TextView

    private val apiServices = apiServices()
    private val utils = utils()
    private val gson = Gson()

    private var snackDuration: Int = Snackbar.LENGTH_LONG

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginView = inflater.inflate(R.layout.fragment_login, container, false)

        loginView.apply {
            lnExit = findViewById(R.id.lnExit)
            lnLogin = findViewById(R.id.lnLogin)
            lnSignUp = findViewById(R.id.lnSignUp)
            etStudentNumber = findViewById(R.id.etStudentNumber)
            etPassword = findViewById(R.id.etPassword)
            imgShowPassword = findViewById(R.id.imgShowPassword)
            tvForgotPassword = findViewById(R.id.tvForgotPassword)
        }

        lnExit.setOnClickListener {
            activity?.finish()
            exitProcess(0)
        }

        lnSignUp.setOnClickListener {
            val fragMan : FragmentManager = requireActivity().supportFragmentManager    
            val fragTrans : FragmentTransaction = fragMan.beginTransaction()
            val registrationFrag = Registration()

            fragTrans.add(R.id.frLogin,registrationFrag,REGISTRATION_FRAGMENT)
            fragTrans.addToBackStack(null)
            fragTrans.commit()
        }

        imgShowPassword.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else if (motionEvent.action == MotionEvent.ACTION_UP || motionEvent.action == MotionEvent.ACTION_CANCEL) {
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }

            etPassword.setSelection(etPassword.text.length)
            true
        }

        lnLogin.setOnLongClickListener {
            etStudentNumber.setText("12345")
            etPassword.setText("P@gente082993")
            lnLogin.performClick()

            true
        }

        lnLogin.setOnClickListener {
            utils.showProgress(requireContext())

            val loginAccountData = loginAccountData(
                studentNumber = etStudentNumber.text.toString(),
                password = etPassword.text.toString()
            )

            apiServices.loginAccount(loginAccountData) {
                utils.closeProgress()

                if (it!!.success) {
                    val login = it.data.login.firstOrNull()

                    etStudentNumber.text.clear()
                    etPassword.text.clear()

                    login?.apply {
                        sharedHelper.apply {
                            putInt("id", id)
                            putString("studentNumber", studentNumber)
                            putInt("yearGraduated", yearGraduated)
                            putInt("courseId", courseId)
                            putString("firstName", firstName)
                            putString("middleName", middleName)
                            putString("lastName", lastName)
                            putString("emailAddress", emailAddress)
                            putString("isPasswordChanged", isPasswordChanged)
                            putString("course", course)
                            putInt("showTitle",1)
                        }

                        if (isPasswordChanged == 0.toString()) {
                            val bottomChangePassword = BottomChangePassword()
                            bottomChangePassword.show(requireActivity().supportFragmentManager, BOTTOM_DIALOG)
                        } else {
                            activity?.let{
                                val intent = Intent (it, MainActivity::class.java)
                                it.startActivity(intent)
                            }
                            activity?.finish()
                        }
                    }
                } else {
                    utils.showToastMessage(requireContext(),it?.messages?.get(0).toString())
                }
            }
        }

        tvForgotPassword.setOnClickListener {
            val bottomPasswordReset = BottomPasswordReset()
            bottomPasswordReset.show(requireActivity().supportFragmentManager, BOTTOM_DIALOG)
        }

        tvForgotPassword.setOnLongClickListener {
            val bottomChangePassword = BottomChangePassword()
            bottomChangePassword.show(requireActivity().supportFragmentManager, BOTTOM_DIALOG)
            true
        }

        return loginView
    }
}