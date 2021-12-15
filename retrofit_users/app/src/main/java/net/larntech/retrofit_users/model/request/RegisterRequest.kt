package net.larntech.retrofit_users.model.request

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val date_joined: String,
)