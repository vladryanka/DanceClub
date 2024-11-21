package com.example.danceclub.data.response

import com.example.danceclub.data.model.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonResponse(

    @SerialName("message")
    private val message: String? = null,

    @SerialName("persons")
    private val persons: List<Person>? = null
) {
    fun getPersons(): List<Person>? {
        return persons
    }
}