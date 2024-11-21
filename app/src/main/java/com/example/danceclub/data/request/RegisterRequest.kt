package com.example.danceclub.data.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val surname: String,
    val patronimic: String,
    val phone: String,
    val password: String,
    val birth_date: Long
)
