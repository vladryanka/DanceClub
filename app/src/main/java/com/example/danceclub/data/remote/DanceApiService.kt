package com.example.danceclub.data.remote

import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Token
import com.example.danceclub.data.model.Training
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL: String = "https://mega-prod.ru/"

private val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
/*
private val authInterceptor = Interceptor { chain ->
    val originalRequest = chain.request()
    // Предположим, что вы храните токен где-то в вашем приложении
    val token = "ваш_токен" // Замените на ваш текущий токен
    val newRequest = originalRequest.newBuilder()
        .addHeader("Authorization", "Bearer $token") // Добавляем заголовок Authorization
        .build()
    chain.proceed(newRequest) // Выполняем запрос с новым заголовком
}
*/
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
    suspend fun loadSignedTrainingResponse(): Training

    @Headers("Content-Type: application/json")
    @GET("person/all")
    suspend fun loadPersonsResponse(@Header("Authorization") token: String): PersonResponse

    @POST("person/add")
    suspend fun pushNewPerson(@Body person: Person)

    @POST("training/add")
    suspend fun pushNewTraining(@Body training: Training)

    @POST("auth/login")
    suspend fun login(@Body phone:String, password:String): Response

    @POST("auth/logout")
    suspend fun logout(@Body training: Training)

    @POST("auth/newTokens")
    suspend fun newTokens(@Body training: Training)

    @POST("auth/newAccessToken")
    suspend fun newAccessToken(@Body training: Training)

    @POST("auth/changePassword")
    suspend fun changePassword(@Body training: Training)

    @POST("auth/register")
    suspend fun register(@Body training: Training)


}

object DanceApi {
    val retrofitService: DanceApiService by lazy {
        retrofit.create(DanceApiService::class.java)
    }
}