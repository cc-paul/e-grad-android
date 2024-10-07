package com.jmr.e_grad.`interface`

import com.jmr.e_grad.data.AchievementResponse
import com.jmr.e_grad.data.AwardeeResponse
import com.jmr.e_grad.data.ChangePasswordData
import com.jmr.e_grad.data.GetGraduatesResponse
import com.jmr.e_grad.data.LoginResponse
import com.jmr.e_grad.data.MediaResponse
import com.jmr.e_grad.data.ResetPasswordData
import com.jmr.e_grad.data.YearBookRelatedData
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

interface getCourseGradAPI {
    @Headers("Content-Type: application/json")
    @POST("yearbook-related")
    fun getCourseGrad(@Body yearBookRelatedData: YearBookRelatedData) : Call<courseResponse>
}


interface getAllGradAPI {
    @Headers("Content-Type: application/json")
    @POST("yearbook-related")
    fun getAllGrads(@Body yearBookRelatedData: YearBookRelatedData) : Call<GetGraduatesResponse>
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

interface getAwardeeAPI {
    @Headers("Content-Type: application/json")
    @POST("yearbook-related")
    fun getAwardee(@Body requestBody: YearBookRelatedData) : Call<AwardeeResponse>
}

interface getMediaAPI {
    @Headers("Content-Type: application/json")
    @POST("yearbook-related")
    fun getMedia(@Body requestBody: YearBookRelatedData) : Call<MediaResponse>
}

interface getAchievementAPI {
    @Headers("Content-Type: application/json")
    @POST("yearbook-related")
    fun getAchievement(@Body requestBody: YearBookRelatedData) : Call<AchievementResponse>
}