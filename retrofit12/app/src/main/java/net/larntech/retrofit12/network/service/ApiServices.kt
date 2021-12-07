package net.larntech.retrofit12.network.service

import net.larntech.retrofit12.network.model.request.AuthRequest
import net.larntech.retrofit12.network.model.request.RegisterUserRequest
import net.larntech.retrofit12.network.model.response.AuthResponse
import net.larntech.retrofit12.network.model.response.RegisterUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

@POST("/authenticate/")
fun loginUser(@Body authRequest: AuthRequest): Call<AuthResponse>

@POST("/users/")
fun registerUser(@Body registerUserRequest: RegisterUserRequest): Call<RegisterUserResponse>


}