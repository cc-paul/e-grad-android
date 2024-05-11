package com.jmr.e_grad.data

import com.google.gson.annotations.SerializedName

class ResetPasswordData (
    @SerializedName("mode") val mode: String = "",
    @SerializedName("emailAddress") val emailAddress: String = ""
)