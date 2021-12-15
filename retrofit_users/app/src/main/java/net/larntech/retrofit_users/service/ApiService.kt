package net.larntech.retrofit_users.service

import net.larntech.retrofit_users.model.request.LoginRequest
import net.larntech.retrofit_users.model.request.RegisterRequest
import net.larntech.retrofit_users.model.response.LoginResponse
import net.larntech.retrofit_users.model.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/authenticate/")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/users/")
    fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>

}