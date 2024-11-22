package com.example.danceclub.data.remote

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.local.dao.TrainingSignDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Token
import com.example.danceclub.data.model.Training
import com.example.danceclub.data.model.TrainingSign
import com.example.danceclub.data.request.ImageRequest
import com.example.danceclub.data.request.LoginRequest
import com.example.danceclub.data.request.RegisterRequest
import com.example.danceclub.utils.base64StringToImageBitmap
import com.example.danceclub.utils.uriToBase64String
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Base64
import java.util.Date

class DanceRepository(
    private val personDao: PersonsDao,
    private val trainingDao: TrainingDao,
    private val trainingSignDao: TrainingSignDao,
) {

    lateinit var token: Token
    lateinit var currentPerson: Person

    suspend fun fetchAndSavePersons(token: String) {
        val personResponse = DanceApi.retrofitService.loadPersonsResponse("Bearer $token")
        val persons = personResponse.getPersons() ?: return

        val personList = personDao.getPersons().firstOrNull()

        withContext(Dispatchers.IO) {
            for (person in persons) {
                if (personList != null)
                    if (!personList.contains(person)) {
                        Log.d("Doing", person.toString())
                        personDao.add(person)
                    }
            }
        }
    }

    suspend fun postSign(training: Training, personId: String): String? {
        val response = DanceApi.retrofitService.addSign("Bearer ${token.accessToken}", training.id)
        val result = handleApi { response }
        var errorType: String? = null
        when (result) {
            is NetworkResult.Success -> {
                trainingSignDao.add(TrainingSign(trainingId = training.id, personId = personId))
            }

            is NetworkResult.Error -> {
                if (result.code != 409) {
                    errorType = result.errorMsg
                    Log.d("Doing", "login failed: $errorType")
                }
            }

            is NetworkResult.Exception -> {
                Log.d("Exception", result.e.toString())
                errorType = "Неизвестная ошибка"
            }
        }
        return errorType
    }

    private fun decodeJWT(token: String): Long? {

        val parts = token.split(".")

        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid JWT token")
        }

        val payload = parts[1]
        val decodedBytes = Base64.getUrlDecoder().decode(payload)

        val objectMapper = ObjectMapper()
        val jsonNode: JsonNode = objectMapper.readTree(decodedBytes)

        return jsonNode.get("exp")?.asLong()
    }

    private fun isTokenExpired(exp: Long?): Boolean {
        if (exp != null) {
            val expirationDate = Date(exp * 1000)
            val currentDate = Date()
            return expirationDate.before(currentDate)
        }
        return true
    }

    private fun getCurrentAccessToken() {
        val exp = decodeJWT(token.accessToken)
        if (exp != null) {
            if (isTokenExpired(exp)) { // Токен AccessToken истек
                val expRefresh = decodeJWT(token.refreshToken)
                if (expRefresh != null) { // Токен RefreshToken истек
                    if (isTokenExpired(expRefresh)) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val newToken =
                                DanceApi.retrofitService.newTokens("Bearer ${token.refreshToken}")
                            token = newToken.body()?.let {
                                Token(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }!!
                        }
                    }

                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        val newAccessToken =
                            DanceApi.retrofitService.newAccessToken("Bearer ${token.refreshToken}")
                        token = newAccessToken.body()?.let {
                            Token(
                                accessToken = it.accessToken,
                                refreshToken = token.refreshToken
                            )
                        }!!
                    }
                }
            }
        } else {
            Log.d("Doing", "Ключ 'exp' не найден.")
        }
    }

    suspend fun login(phone: String, password: String): String? {
        val result = handleApi { DanceApi.retrofitService.login(LoginRequest(phone, password)) }
        var errorType: String? = null

        when (result) {
            is NetworkResult.Success -> {
                token = result.data.tokens // Инициализируем token
                Log.d("Doing", token.toString())
                currentPerson = result.data.person
                getCurrentAccessToken()
                Log.d("Doing", token.toString())
            }

            is NetworkResult.Error -> {
                errorType = result.errorMsg
                Log.d("Doing", "login failed: $errorType")
            }

            is NetworkResult.Exception -> {
                Log.d("Exception", result.e.toString())
                errorType = "Неизвестная ошибка"
            }
        }

        return errorType
    }

    suspend fun putImage(image: Uri, contentResolver: ContentResolver): String? {
        val imageString = uriToBase64String(contentResolver, image)
        var errorType: String? = null
        if (imageString != null) {
            getCurrentAccessToken()
            val response = DanceApi.retrofitService
                .putImage(
                    "Bearer ${token.accessToken}",
                    ImageRequest(imageString)
                )
            val result = handleApi { response }
            Log.d("Doing", result.toString())
            when (result) {
                is NetworkResult.Success -> {
                    errorType = "1"
                }

                is NetworkResult.Error -> {
                    // Log.d("Doing",apiService.getAdminLogs().body().toString())
                    errorType = result.errorMsg
                    Log.d("Error", "$errorType")
                }

                is NetworkResult.Exception -> {
                    //Log.d("Doing",apiService.getAdminLogs().body().toString())
                    Log.d("Exception", result.e.toString())
                    errorType = "Неизвестная ошибка"
                }
            }
        }
        return errorType
    }

    suspend fun getImage(): ImageBitmap? {
        getCurrentAccessToken()
        val response = DanceApi.retrofitService.getImage("Bearer ${token.accessToken}")
        val result = handleApi { response }

        return when (result) {
            is NetworkResult.Success -> {
                result.data.image?.let { base64StringToImageBitmap(it) }
            }

            is NetworkResult.Error -> {
                Log.d("Error", "В профиле в Репозитории getImage ${result.errorMsg}")
                null
            }

            is NetworkResult.Exception -> {
                Log.d("Exception", "В профиле в Репозитории getImage" + result.e.toString())
                null
            }
        }
    }

    suspend fun register(
        name: String,
        surname: String,
        patronymic: String,
        phone: String,
        password: String, birthday: LocalDate,
    ): Pair<String?, Person?> {
        Log.d("Doing", "$name $surname $patronymic $phone $password $birthday")

        val registerResponse = DanceApi.retrofitService.register(
            RegisterRequest(
                name,
                surname,
                patronymic,
                phone,
                password,
                birthday.atStartOfDay(ZoneOffset.UTC).toEpochSecond()
            )
        )
        Log.d("Doing", registerResponse.toString())

        val result = handleApi { registerResponse }
        var errorType: String? = null
        var person: Person? = null

        when (result) {
            is NetworkResult.Success -> {
                token = result.data.tokens // Инициализируем token
                person = result.data.person
                currentPerson = person
                getCurrentAccessToken()
            }

            is NetworkResult.Error -> {
                errorType = result.errorMsg
                Log.d("Error", "Registration failed: $errorType")
            }

            is NetworkResult.Exception -> {
                Log.d("Exception", result.e.toString())
                errorType = "Неизвестная ошибка"
            }
        }

        return Pair(errorType, person)
    }


    suspend fun fetchAndSaveSignedTrainings() {
        val trainingSignedResponse = DanceApi.retrofitService.loadSignedTrainingResponse(
            "Bearer ${token.accessToken}"
        ).trainings ?: return
        val trainingList = trainingSignDao.getTrainingSignsSync()
        withContext(Dispatchers.IO) {
            val existingTrainingIds = trainingList.map { it.trainingId }.toSet()

            for (training in trainingSignedResponse) {
                if (!existingTrainingIds.contains(training.id)) {
                    trainingSignDao.add(TrainingSign(currentPerson.id, training.id))
                }
            }
        }
    }

    suspend fun fetchAndSaveTrainings() {
        val trainingResponse = DanceApi.retrofitService.loadTrainingsResponse().trainings
        val trainingList = trainingDao.getTrainingsSync()
        val existingTrainingIds = trainingList.map { it.id }.toSet()
        withContext(Dispatchers.IO) {
            for (training in trainingResponse!!) {
                if (!existingTrainingIds.contains(training.id)) {
                    trainingDao.add(training)
                }
            }
        }
    }


}
