package net.larntech.retrofit12.network.model.request

data class RegisterUserRequest(
    val username: String,
    val email: String,
    val date_joined: String,
    val password: String)
