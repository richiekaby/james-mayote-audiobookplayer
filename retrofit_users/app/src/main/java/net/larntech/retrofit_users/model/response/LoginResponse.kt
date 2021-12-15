package net.larntech.retrofit_users.model.response

data class LoginResponse(
    val user_id: Int,
    val username: String,
    val email: String
)