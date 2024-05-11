package com.jmr.e_grad.data

data class LoginResponse(
    val statusCode: Int,
    val success: Boolean,
    val messages: List<String>,
    val data: LoginData
)

data class LoginData(
    val rows_returned: Int,
    val login: List<Login>
)

data class Login(
    val id: Int,
    val studentNumber: String,
    val yearGraduated: Int,
    val courseId: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val emailAddress: String,
    val isPasswordChanged: String,
    val course: String
)
