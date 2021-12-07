package net.larntech.retrofit12.network.model.response

data class RegisterUserResponse (

    val id: Int,
    val url: String,
    val username: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val is_active: Boolean,
    val date_joined: String,
    val last_login: String

        )
