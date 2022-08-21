package com.mondialrelay.chucknorrisapp.domain.model

data class JokeModel(
    val text: String,
    var rating: Float,
    val createdAt: String = "",
    val id: String = "",
)