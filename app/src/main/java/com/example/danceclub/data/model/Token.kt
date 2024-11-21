package com.example.danceclub.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Token(
    val refreshToken: String,
    val accessToken: String
)
