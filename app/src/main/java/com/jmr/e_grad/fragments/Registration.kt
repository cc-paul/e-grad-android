package com.jmr.e_grad.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jmr.e_grad.R
import com.jmr.e_grad.data.registrationData
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils

class Registration : Fragment(), dataTransfer {
    private lateinit var registrationView: View
    private lateinit var etStudentNumber: EditText
    private lateinit var etYearGraduated: EditText
    private lateinit var etCourse: EditText
    private lateinit var etFirstName: EditText
    private lateinit var etMiddleName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmailAddress: EditText
    private lateinit var lnBack: LinearLayout
    private lateinit var lnRegister: LinearLayout
    private lateinit var lnSignIn: LinearLayout
    private lateinit var lnRegistration: LinearLayout

    private val apiServices = apiServices()
    private val utils = utils()
    private val gson = Gson()

    private var courseId: Int = 0
    private var snackDuration: Int = Snackbar.LENGTH_LONG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registrationView = inflater.inflate(R.layout.fragment_registration, container, false)

        registrationView.apply {
            etStudentNumber = findViewById(R.id.etStudentNumber)
            etYearGraduated = findViewById(R.id.etYearGraduated)
            etCourse = findViewById(R.id.etCourse)
            etFirstName = findViewById(R.id.etFirstName)
            etMiddleName = findViewById(R.id.etMiddleName)
            etLastName = findViewById(R.id.etLastName)
            etEmailAddress = findViewById(R.id.etEmailAddress)
            lnBack = findViewById(R.id.lnBack)
            lnRegister = findViewById(R.id.lnRegister)
            lnSignIn = findViewById(R.id.lnSignIn)
            lnRegistration = findViewById(R.id.lnRegistration)
        }

        etCourse.setOnClickListener {
            utils.showProgress(requireContext())

            apiServices.getCourse {
                utils.closeProgress()

                if (it!!.success) {
                    val courseListFragMan : FragmentManager = requireActivity().supportFragmentManager
                    val courseListFragTrans : FragmentTransaction = courseListFragMan.beginTransaction()
                    val bundle = Bundle().apply {
                        putString("courseJson", gson.toJson(it.data))
                    }

                    val courseListFrag = CourseList().apply {
                        arguments = bundle
                    }

                    courseListFragTrans.apply {
                        add(R.id.frLogin,courseListFrag)
                        addToBackStack(null)
                        commit()
                    }
                } else {
                    utils.showToastMessage(requireContext().applicationContext, it?.messages?.get(0).toString())
                }
            }
        }

        lnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        lnRegister.setOnClickListener {
            utils.showProgress(requireContext())

            val yearGraduatedText = etYearGraduated.text.toString()
            val yearGraduated = if (yearGraduatedText.isNotBlank()) {
                yearGraduatedText.toIntOrNull() ?: 0
            } else {
                0
            }

            val registrationInfo = registrationData(
                studentNumber = etStudentNumber.text.toString(),
                yearGraduated = yearGraduated,
                courseId = courseId,
                firstName = etFirstName.text.toString(),
                middleName = etMiddleName.text.toString(),
                lastName = etLastName.text.toString(),
                emailAddress = etEmailAddress.text.toString()
            )

            apiServices.createAccount(registrationInfo) {
                utils.closeProgress()

                if (it!!.success) {
                    etStudentNumber.text.clear()
                    etYearGraduated.text.clear()
                    etFirstName.text.clear()
                    etMiddleName.text.clear()
                    etLastName.text.clear()
                    etEmailAddress.text.clear()
                    snackDuration = Snackbar.LENGTH_LONG
                }

                utils.showSnackMessage(lnRegistration,it?.messages?.get(0).toString(),snackDuration)
            }
        }

        lnSignIn.setOnClickListener {
            lnBack.performClick()
        }

        return registrationView
    }

    override fun passCourseDataToRegistration(_courseId: Int, course: String) {
        courseId = _courseId
        etCourse.setText(course)
    }

}