package com.example.danceclub.data.response

import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val person: Person,
    val tokens: Token
)
