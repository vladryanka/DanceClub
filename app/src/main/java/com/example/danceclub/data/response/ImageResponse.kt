package com.example.danceclub.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val message: String,
    val image: String?
)