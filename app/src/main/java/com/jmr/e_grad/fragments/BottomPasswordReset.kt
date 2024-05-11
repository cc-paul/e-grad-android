package com.jmr.e_grad.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmr.e_grad.Constants
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.ResetPasswordData
import com.jmr.e_grad.data.loginAccountData
import com.jmr.e_grad.helper.sharedHelper
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils


class BottomPasswordReset : BottomSheetDialogFragment() {
    private lateinit var viewPasswordReset : View
    private lateinit var etEmailAddress: EditText
    private lateinit var lnPasswordReset: LinearLayout

    private val apiServices = apiServices()
    private val utils = utils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewPasswordReset = inflater.inflate(R.layout.fragment_bottom_password_reset, container, false)

        viewPasswordReset.apply {
            etEmailAddress = findViewById(R.id.etEmailAddress)
            lnPasswordReset = findViewById(R.id.lnPasswordReset)
        }

        lnPasswordReset.setOnClickListener {
            utils.showProgress(requireContext())

            val resetPasswordData = ResetPasswordData(
                mode = "forgot_password",
                emailAddress = etEmailAddress.text.toString()
            )

            apiServices.resetPassword(resetPasswordData) {
                utils.closeProgress()
                utils.showToastMessage(requireContext(), it?.messages?.get(0).toString())

                if (it!!.success) {
                    dismiss()
                }
            }
        }

        return viewPasswordReset
    }
}