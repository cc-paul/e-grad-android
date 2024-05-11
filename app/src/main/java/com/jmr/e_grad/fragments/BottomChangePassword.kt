package com.jmr.e_grad.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmr.e_grad.R
import com.jmr.e_grad.data.ChangePasswordData
import com.jmr.e_grad.data.loginAccountData
import com.jmr.e_grad.helper.sharedHelper
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils

class BottomChangePassword : BottomSheetDialogFragment() {
    private lateinit var viewBottomChangePassword: View
    private lateinit var etNewPassword: EditText
    private lateinit var etRepeatPassword: EditText
    private lateinit var imgShowNewPassword: ImageView
    private lateinit var imgShowRepeatPassword: ImageView
    private lateinit var chkValidation_1: CheckBox
    private lateinit var chkValidation_2: CheckBox
    private lateinit var chkValidation_3: CheckBox
    private lateinit var chkValidation_4: CheckBox
    private lateinit var chkValidation_5: CheckBox
    private lateinit var lnChangePassword: LinearLayout
    private lateinit var tvTitle: TextView

    private val apiServices = apiServices()
    private val utils = utils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBottomChangePassword = inflater.inflate(R.layout.bottom_change_password, container, false)

        viewBottomChangePassword.apply {
            etNewPassword = findViewById(R.id.etNewPassword)
            etRepeatPassword = findViewById(R.id.etRepeatPassword)
            imgShowNewPassword = findViewById(R.id.imgShowNewPassword)
            imgShowRepeatPassword = findViewById(R.id.imgShowRepeatPassword)
            chkValidation_1 = findViewById(R.id.chkValidation_1)
            chkValidation_2 = findViewById(R.id.chkValidation_2)
            chkValidation_3 = findViewById(R.id.chkValidation_3)
            chkValidation_4 = findViewById(R.id.chkValidation_4)
            chkValidation_5 = findViewById(R.id.chkValidation_5)
            lnChangePassword = findViewById(R.id.lnChangePassword)
            tvTitle = findViewById(R.id.tvTitle)
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

        lnChangePassword.setOnClickListener {
            utils.showProgress(requireContext())

            val changePasswordData = ChangePasswordData(
                mode = "change_password",
                id = sharedHelper.getInt("id"),
                newPassword = etNewPassword.text.toString(),
                repeatPassword = etRepeatPassword.text.toString()
            )

            Log.e("Password Body",changePasswordData.toString())

            apiServices.changePassword(changePasswordData) {
                utils.closeProgress()

                if (it!!.success) {
                    dismiss()
                }

                utils.showToastMessage(requireContext(),it?.messages?.get(0).toString())
            }
        }

        tvTitle.visibility = if (sharedHelper.getInt("showTitle") == 1) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }

        return viewBottomChangePassword
    }
}