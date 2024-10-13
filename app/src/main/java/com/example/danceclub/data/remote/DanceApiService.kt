package com.example.danceclub.data.remote

import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Training
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL: String = "http://195.54.178.243:25433/"

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

val apiService: DanceApiService = retrofit.create(DanceApiService::class.java)

interface DanceApiService {
    @GET("training/all")
    suspend fun loadTrainingsResponse(): TrainingResponse

    @GET("training/ по id") // TODO
    suspend fun loadSelectedTrainingResponse(): Training

    @GET("training/signed")
    suspend fun loadSignedTrainingResponse(): Training

    @GET("person/all")
    suspend fun loadPersonsResponse(): PersonResponse

    /* TODO: добавить POST-запросы для записи*/
}

object DanceApi {
    val retrofitService: DanceApiService by lazy {
        retrofit.create(DanceApiService::class.java)
    }
}