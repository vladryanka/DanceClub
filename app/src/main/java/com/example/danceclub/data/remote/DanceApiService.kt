package com.example.danceclub.data.remote

import com.example.danceclub.data.model.Account
import com.example.danceclub.data.model.Section
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL: String = "ENTER the LINK" // TODO

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

val apiService: DanceApiService = retrofit.create(DanceApiService::class.java)

interface DanceApiService {
    @GET("GET секций") // TODO
    suspend fun loadSectionsResponse(): List<Section>

    @GET("GET аккаунтов") // TODO
    suspend fun loadAccountsResponse(): List<Account>

    /* TODO: добавить POST-запросы для записи*/
}

object DanceApi {
    val retrofitService: DanceApiService by lazy {
        retrofit.create(DanceApiService::class.java)
    }
}