package com.example.danceclub.data.remote

import android.util.Log
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Token
import com.example.danceclub.data.model.Training
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.util.Base64
import java.util.Date
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.userAgent
import java.util.UUID

class DanceRepository(private val personDao: PersonsDao, private val trainingDao: TrainingDao) {

    lateinit var token: Token

    suspend fun fetchAndSavePersons(token: String) {
        val personResponse = apiService.loadPersonsResponse("Bearer $token")
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
                        CoroutineScope(Dispatchers.IO).launch(){
                            val newToken =apiService.newTokens("Bearer $token.refreshToken")
                            token = newToken.body()?.let {
                                Token(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }!!
                        }
                    }

                } else {
                    CoroutineScope(Dispatchers.IO).launch(){
                        val newAccessToken = apiService.newAccessToken("Bearer $token.refreshToken")
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
        val result = handleApi { apiService.login(phone, password) }
        var errorType: String? = null
        when (result) {
            is NetworkResult.Success -> {
                getCurrentAccessToken()
                result.data.let {
                    token = Token(it.refreshToken, it.accessToken)
                }
            }

            is NetworkResult.Error -> {
                errorType = result.errorMsg.toString()
            }

            is NetworkResult.Exception -> {
                errorType = "Неизвестная ошибка"
            }
        }
        return errorType
    }

    suspend fun register(
        name : String,
        surname : String,
        patronimic : String,
        age : Int,
        phone : String, password: String
    ): Pair<String?, Person?> {
        Log.d("Doing","$name $surname $patronimic $age $phone $password")
        val register = apiService.register(
            name, surname,
            patronimic, age,
            phone, password
        )
        val result = handleApi {
            register
        }
        var errorType: String? = null
        var person: Person? = null
        when (result) {
            is NetworkResult.Success -> {
                getCurrentAccessToken()
                val token = result.data
                person = token.person
                this.token = Token(token.token.refreshToken, token.token.accessToken)
            }

            is NetworkResult.Error -> {
                errorType = result.errorMsg.toString()
            }

            is NetworkResult.Exception -> {
                Log.d("Doing", result.e.toString())
                errorType = "Неизвестная ошибка"
            }
        }
        return Pair(errorType, person)
    }


    suspend fun pushAndSaveTraining(training: Training) {
        return withContext(Dispatchers.IO) {
            apiService.pushNewTraining(training)
        }
    }

    suspend fun fetchAndSaveTrainings() {
        val trainingResponse = apiService.loadTrainingsResponse().trainings
        Log.d("Doing", trainingResponse.toString())
        val trainingList = trainingDao.getTrainingsSync()
        Log.d("Doing", trainingList.toString())
        withContext(Dispatchers.IO) {
            for (training in trainingResponse!!) {
                if (!trainingList.contains(training)) {
                    trainingDao.add(training)
                }
            }
        }
    }


    suspend fun getSignedTrainings() {
        //return apiService.loadSignedTrainingResponse()
    }


}
