package com.jmr.e_grad.data

import com.google.gson.annotations.SerializedName

data class loginAccountData(
    @SerializedName("studentNumber") val studentNumber: String = "",
    @SerializedName("password") val password: String = "",
)