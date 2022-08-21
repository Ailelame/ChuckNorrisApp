package com.mondialrelay.chucknorrisapp.infrastructure.data.http.iochucknorris


import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JokeDto(
    @Json(name = "categories")
    val categories: List<String>?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "icon_url")
    val iconUrl: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "url")
    val url: String?,
    @Json(name = "value")
    val value: String?
) {
    fun toModel(): JokeModel =
        JokeModel(
            text = value ?: "",
            rating = 2.5f,
            createdAt = createdAt ?: "",
            id = id ?: "",
        )
}