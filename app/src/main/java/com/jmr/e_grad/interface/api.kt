package com.jmr.e_grad.`interface`

import com.jmr.e_grad.data.ChangePasswordData
import com.jmr.e_grad.data.LoginResponse
import com.jmr.e_grad.data.ResetPasswordData
import com.jmr.e_grad.data.courseResponse
import com.jmr.e_grad.data.insertResponse
import com.jmr.e_grad.data.loginAccountData
import com.jmr.e_grad.data.registrationData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface getCourseAPI {
    @GET("course/all-course")
    fun getCourse() : Call<courseResponse>
}

interface createAccountAPI {
    @Headers("Content-Type: application/json")
    @POST("account/register-account")
    fun createAccount(@Body requestBody: registrationData) : Call<insertResponse>
}

interface loginAccountAPI {
    @Headers("Content-Type: application/json")
    @POST("account/login")
    fun loginAccount(@Body requestBody: loginAccountData) : Call<LoginResponse>
}

interface changePasswordAPI {
    @Headers("Content-Type: application/json")
    @POST("account/accounts-related")
    fun changePassword(@Body requestBody: ChangePasswordData) : Call<insertResponse>
}

interface resetPasswordAPI {
    @Headers("Content-Type: application/json")
    @POST("account/accounts-related")
    fun resetPassword(@Body requestBody: ResetPasswordData) : Call<insertResponse>
}