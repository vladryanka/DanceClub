package com.example.danceclub.data.remote

import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.local.dao.TrainingSignDao
import com.example.danceclub.data.request.LoginRequest
import com.example.danceclub.data.request.RegisterRequest
import com.example.danceclub.data.response.RegistrationResponse
import com.example.danceclub.data.model.Token
import com.example.danceclub.data.model.Training
import com.example.danceclub.data.response.LoginResponse
import com.example.danceclub.data.response.PersonResponse
import com.example.danceclub.data.response.TrainingResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val BASE_URL: String = "https://mega-prod.ru/"

private val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}
val json = Json {
    ignoreUnknownKeys = true
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .connectTimeout(60, TimeUnit.SECONDS) // Увеличение времени ожидания подключения
    .readTimeout(60, TimeUnit.SECONDS)     // Увеличение времени ожидания чтения
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

val apiService: DanceApiService = retrofit.create(DanceApiService::class.java)

interface DanceApiService {
    @GET("training/all")
    suspend fun loadTrainingsResponse(): TrainingResponse

    @GET("training/signed")
    suspend fun loadSignedTrainingResponse(): TrainingResponse

    @GET("person/all")
    suspend fun loadPersonsResponse(@Header("Authorization") bearerToken: String): PersonResponse

    @POST("training/add")
    suspend fun pushNewTraining(@Body training: Training)

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @POST("auth/newTokens")
    suspend fun newTokens(@Header("Authorization") bearerToken: String): Response<Token>


    @POST("auth/newAccessToken")
    suspend fun newAccessToken(@Header("Authorization") bearerToken: String): Response<Token>

    @POST("auth/changePassword")
    suspend fun changePassword(
        @Header("Authorization") bearerToken: String,
        @Body training: Training
    )

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegistrationResponse>

    @POST("sign/add")
    suspend fun addSign(
        @Header("Authorization") bearerToken: String,
        @Query("trainingId")trainingId: String
    ):Response<Void>

    @PUT("person/picture")
    suspend fun putImage(
        @Header("Authorization") bearerToken: String,
        @Body image: String
    ):Response<Void>

    @GET("person/picture")
    suspend fun getImage(
        @Header("Authorization") bearerToken: String
    ):Response<String>
}

object DanceApi {
    val retrofitService: DanceApiService by lazy {
        retrofit.create(DanceApiService::class.java)
    }
}

object RepositoryProvider {

    private lateinit var repository: DanceRepository

    fun initialize(personsDao: PersonsDao, trainingDao: TrainingDao, trainingSignDao: TrainingSignDao) {
        repository = DanceRepository(personsDao, trainingDao, trainingSignDao)
    }

    fun getRepository(): DanceRepository {
        if (!::repository.isInitialized) {
            throw IllegalStateException("Repository is not initialized. Call initialize() first.")
        }
        return repository
    }
}