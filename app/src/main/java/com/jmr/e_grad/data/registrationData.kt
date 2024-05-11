package com.jmr.e_grad.data

import com.google.gson.annotations.SerializedName

data class registrationData(
    @SerializedName("studentNumber") val studentNumber: String = "",
    @SerializedName("yearGraduated") val yearGraduated: Int = 0,
    @SerializedName("courseId") val courseId: Int = 0,
    @SerializedName("firstName") val firstName: String = "",
    @SerializedName("middleName") val middleName: String = "",
    @SerializedName("lastName") val lastName: String = "",
    @SerializedName("emailAddress") val emailAddress: String = ""
)
