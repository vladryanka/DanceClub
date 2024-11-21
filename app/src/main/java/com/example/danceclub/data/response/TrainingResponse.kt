package com.example.danceclub.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.example.danceclub.data.model.Training

@Serializable
data class TrainingResponse(
    @SerialName("message")
    val message: String? = null,
    @SerialName("trainings")
    val trainings: List<Training>? = null
)