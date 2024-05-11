package com.jmr.e_grad.data

import com.google.gson.annotations.SerializedName

data class ChangePasswordData(
    @SerializedName("mode") val mode: String = "",
    @SerializedName("id") val id: Int = 0,
    @SerializedName("newPassword") val newPassword: String = "",
    @SerializedName("repeatPassword") val repeatPassword: String = "",
)
