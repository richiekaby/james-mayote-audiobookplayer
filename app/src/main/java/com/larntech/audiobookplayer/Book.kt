package com.larntech.audiobookplayer

data class Book(
    val id: Int,
    val duration: Int,
    val title: String,
    val author: String,
    val cover_url: String
)