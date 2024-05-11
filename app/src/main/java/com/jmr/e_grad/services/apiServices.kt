package com.jmr.e_grad.services

import android.util.Log
import com.google.gson.Gson
import com.jmr.e_grad.data.ChangePasswordData
import com.jmr.e_grad.data.LoginResponse
import com.jmr.e_grad.data.ResetPasswordData
import com.jmr.e_grad.data.courseResponse
import com.jmr.e_grad.data.insertResponse
import com.jmr.e_grad.data.loginAccountData
import com.jmr.e_grad.data.registrationData
import com.jmr.e_grad.`interface`.changePasswordAPI
import com.jmr.e_grad.`interface`.createAccountAPI
import com.jmr.e_grad.`interface`.getCourseAPI
import com.jmr.e_grad.`interface`.loginAccountAPI
import com.jmr.e_grad.`interface`.resetPasswordAPI
import com.jmr.e_grad.retrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class apiServices {
    fun getCourse(onResult: (courseResponse?) -> Unit) {
        val retrofit = retrofitHelper.buildService(getCourseAPI::class.java)

        retrofit.getCourse().enqueue(
            object : Callback<courseResponse> {
                override fun onFailure(call: Call<courseResponse>, t: Throwable) {
                    Log.e("Error Message",t.message.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<courseResponse>,
                    response: Response<courseResponse>
                ) {
                    var course = response.body()

                    if (!response.isSuccessful) {
                        val gson = Gson()
                        course = gson.fromJson(response.errorBody()!!.string(),courseResponse::class.java)
                    }

                    onResult(course)
                }
            }
        )
    }

    fun createAccount(registrationData: registrationData,onResult: (insertResponse?) -> Unit) {
        val retrofit = retrofitHelper.buildService(createAccountAPI::class.java)

        retrofit.createAccount(registrationData).enqueue(
            object : Callback<insertResponse> {
                override fun onFailure(call: Call<insertResponse>, t: Throwable) {
                    Log.e("Error Message",t.message.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<insertResponse>,
                    response: Response<insertResponse>
                ) {
                    var account = response.body()
                    Log.e("Current JSON Body",account.toString())

                    if (!response.isSuccessful) {
                        val gson = Gson()
                        account = gson.fromJson(response.errorBody()!!.string(),insertResponse::class.java)
                    }

                    onResult(account)
                }
            }
        )
    }

    fun loginAccount(loginAccountData: loginAccountData, onResult: (LoginResponse?) -> Unit) {
        val retrofit = retrofitHelper.buildService(loginAccountAPI::class.java)

        retrofit.loginAccount(loginAccountData).enqueue(
            object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("Error Message",t.message.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    var login = response.body()
                    Log.e("Current JSON Body",login.toString())

                    if (!response.isSuccessful) {
                        val gson = Gson()
                        login = gson.fromJson(response.errorBody()!!.string(),LoginResponse::class.java)
                    }

                    onResult(login)
                }
            }
        )
    }

    fun changePassword(changePasswordData: ChangePasswordData, onResult: (insertResponse?) -> Unit) {
        val retrofit = retrofitHelper.buildService(changePasswordAPI::class.java)

        retrofit.changePassword(changePasswordData).enqueue(
            object : Callback<insertResponse> {
                override fun onFailure(call: Call<insertResponse>, t: Throwable) {
                    Log.e("Error Message",t.message.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<insertResponse>,
                    response: Response<insertResponse>
                ) {
                    var changePassword = response.body()
                    Log.e("Current JSON Body",changePassword.toString())

                    if (!response.isSuccessful) {
                        val gson = Gson()
                        changePassword = gson.fromJson(response.errorBody()!!.string(),insertResponse::class.java)
                    }

                    onResult(changePassword)
                }
            }
        )
    }

    fun resetPassword(resetPasswordData: ResetPasswordData, onResult: (insertResponse?) -> Unit) {
        val retrofit = retrofitHelper.buildService(resetPasswordAPI::class.java)

        retrofit.resetPassword(resetPasswordData).enqueue(
            object : Callback<insertResponse> {
                override fun onFailure(call: Call<insertResponse>, t: Throwable) {
                    Log.e("Error Message",t.message.toString())
                    onResult(null)
                }
                override fun onResponse(
                    call: Call<insertResponse>,
                    response: Response<insertResponse>
                ) {
                    var resetPassword = response.body()
                    Log.e("Current JSON Body",resetPassword.toString())

                    if (!response.isSuccessful) {
                        val gson = Gson()
                        resetPassword = gson.fromJson(response.errorBody()!!.string(),insertResponse::class.java)
                    }

                    onResult(resetPassword)
                }
            }
        )
    }
}