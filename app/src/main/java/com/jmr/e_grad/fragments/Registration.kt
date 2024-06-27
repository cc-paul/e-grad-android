package com.jmr.e_grad.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
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
    private lateinit var etNewPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var imgShowNewPassword: ImageView
    private lateinit var imgShowRepeatPassword: ImageView
    private lateinit var chkValidation_1: CheckBox
    private lateinit var chkValidation_2: CheckBox
    private lateinit var chkValidation_3: CheckBox
    private lateinit var chkValidation_4: CheckBox
    private lateinit var chkValidation_5: CheckBox

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
            etNewPassword = findViewById(R.id.etNewPassword)
            etRepeatPassword = findViewById(R.id.etRepeatPassword)
            imgShowNewPassword = findViewById(R.id.imgShowNewPassword)
            imgShowRepeatPassword = findViewById(R.id.imgShowRepeatPassword)
            chkValidation_1 = findViewById(R.id.chkValidation_1)
            chkValidation_2 = findViewById(R.id.chkValidation_2)
            chkValidation_3 = findViewById(R.id.chkValidation_3)
            chkValidation_4 = findViewById(R.id.chkValidation_4)
            chkValidation_5 = findViewById(R.id.chkValidation_5)
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
            if (!chkValidation_1.isChecked ||
                !chkValidation_2.isChecked ||
                !chkValidation_3.isChecked ||
                !chkValidation_4.isChecked ||
                !chkValidation_5.isChecked
                ) {
                utils.showSnackMessage(lnRegistration,"Password requirements doesn't meet",snackDuration)
                return@setOnClickListener
            }

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
                emailAddress = etEmailAddress.text.toString(),
                password = etNewPassword.text.toString()
            )

            apiServices.createAccount(registrationInfo) {
                utils.closeProgress()

                if (it!!.success) {
                    etStudentNumber.text.clear()
                    etYearGraduated.text.clear()
                    etFirstName.text.clear()
                    etMiddleName.text.clear()
                    etCourse.text.clear()
                    courseId = 0
                    etLastName.text.clear()
                    etEmailAddress.text.clear()
                    etNewPassword.text.clear()
                    etRepeatPassword.text.clear()
                    chkValidation_1.isChecked = false
                    chkValidation_2.isChecked = false
                    chkValidation_3.isChecked = false
                    chkValidation_4.isChecked = false
                    chkValidation_5.isChecked = false
                    snackDuration = Snackbar.LENGTH_LONG
                }

                utils.showSnackMessage(lnRegistration,it?.messages?.get(0).toString(),snackDuration)
            }
        }

        lnSignIn.setOnClickListener {
            lnBack.performClick()
        }

        imgShowNewPassword.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                etNewPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else if (motionEvent.action == MotionEvent.ACTION_UP || motionEvent.action == MotionEvent.ACTION_CANCEL) {
                etNewPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }

            etNewPassword.setSelection(etNewPassword.text.length)
            true
        }

        imgShowRepeatPassword.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                etRepeatPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else if (motionEvent.action == MotionEvent.ACTION_UP || motionEvent.action == MotionEvent.ACTION_CANCEL) {
                etRepeatPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }

            etRepeatPassword.setSelection(etRepeatPassword.text.length)
            true
        }

        etNewPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()

                chkValidation_1.isChecked = password.length >= 8
                chkValidation_2.isChecked = Regex("[a-z]").containsMatchIn(password)
                chkValidation_3.isChecked = Regex("[A-Z]").containsMatchIn(password)
                chkValidation_4.isChecked = Regex("\\d").containsMatchIn(password)
                chkValidation_5.isChecked = Regex("[-!@#\$%^&*()_+=\\[\\]{};':\"\\\\|,.<>?]").containsMatchIn(password)
            }
        })

        return registrationView
    }

    override fun passCourseDataToRegistration(_courseId: Int, course: String) {
        courseId = _courseId
        etCourse.setText(course)
    }

}