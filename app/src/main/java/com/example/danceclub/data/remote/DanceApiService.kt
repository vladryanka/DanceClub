package com.example.danceclub.data.remote

import com.example.danceclub.data.model.RegistrationResponse
import com.example.danceclub.data.model.Token
import com.example.danceclub.data.model.Training
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL: String = "https://mega-prod.ru/"

private val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .build()

val apiService: DanceApiService = retrofit.create(DanceApiService::class.java)

interface DanceApiService {
    @GET("training/all")
    suspend fun loadTrainingsResponse(): TrainingResponse

    @GET("training/signed")
    suspend fun loadSignedTrainingResponse(@Header("Authorization") bearerToken: String): TrainingResponse

    @GET("person/all")
    suspend fun loadPersonsResponse(@Header("Authorization") bearerToken: String): PersonResponse

    @POST("training/add")
    suspend fun pushNewTraining(@Body training: Training)

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("phone") phone: String, @Field("password") password: String
    ): Response<Token>

    @POST("auth/newTokens")
    suspend fun newTokens(@Header("Authorization") bearerToken: String): Response<Token>


    @POST("auth/newAccessToken")
    suspend fun newAccessToken(@Header("Authorization") bearerToken: String): Response<Token>

    @POST("auth/changePassword")
    suspend fun changePassword(
        @Header("Authorization") bearerToken: String,
        @Body training: Training
    )

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("name") name: String,
        @Field("surname") surname: String,
        @Field("patronimic") patronimic: String,
        @Field("age") age: Int,
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Response<RegistrationResponse>
}

object DanceApi {
    val retrofitService: DanceApiService by lazy {
        retrofit.create(DanceApiService::class.java)
    }
}