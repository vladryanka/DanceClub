package com.example.danceclub.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    val tokens: Token,
    val person:Person
)
