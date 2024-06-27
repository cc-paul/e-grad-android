package com.jmr.e_grad.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.jmr.e_grad.Constants
import com.jmr.e_grad.LoginActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.helper.sharedHelper
import kotlin.system.exitProcess

class Account : Fragment() {
    private lateinit var viewAccount: View
    private lateinit var crdChangePassword: CardView
    private lateinit var tvName: TextView
    private lateinit var tvCourse: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvStudentNumber: TextView
    private lateinit var tvSchoolYear: TextView
    private lateinit var imgExit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewAccount = inflater.inflate(R.layout.fragment_account, container, false)

        viewAccount.apply {
            crdChangePassword = findViewById(R.id.crdChangePassword)
            tvName = findViewById(R.id.tvName)
            tvCourse = findViewById(R.id.tvCourse)
            tvEmail = findViewById(R.id.tvEmail)
            tvStudentNumber = findViewById(R.id.tvStudentNumber)
            tvSchoolYear = findViewById(R.id.tvSchoolYear)
            imgExit = findViewById(R.id.imgExit)
        }

        crdChangePassword.setOnClickListener {
            val bottomChangePassword = BottomChangePassword()

            sharedHelper.apply {
                putInt("showTitle",0)
            }

            bottomChangePassword.show(requireActivity().supportFragmentManager,
                Constants.BOTTOM_DIALOG
            )
        }

        imgExit.setOnClickListener {
            activity?.let{
                val intent = Intent (it, LoginActivity::class.java)
                it.startActivity(intent)
            }
            activity?.finish()
        }

        sharedHelper.apply {
            tvName.text = getString("lastName") + ", " + getString("firstName") + " " + getString("middleName")
            tvCourse.text = getString("course")
            tvEmail.text = getString("emailAddress")
            tvStudentNumber.text = getString("studentNumber")
            tvSchoolYear.text = getInt("yearGraduated").toString()
        }

        return viewAccount
    }
}