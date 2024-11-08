package com.example.danceclub.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val surname: String,
    val patronimic: String,
    val age: Int,
    val phone: String,
    val password: String,
    val picture:String
)
