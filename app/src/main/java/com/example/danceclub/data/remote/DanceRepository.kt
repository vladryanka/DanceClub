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
import okhttp3.Response

class DanceRepository(private val personDao: PersonsDao, private val trainingDao: TrainingDao) {

    lateinit var token: Token

    suspend fun fetchAndSavePersons(token:String) {
        val personResponse = apiService.loadPersonsResponse(token)
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

    suspend fun login(phone:String, password:String): Response {
        return apiService.login(phone, password)
    }

    suspend fun pushAndSavePersons(person: Person) {
        return withContext(Dispatchers.IO) {
            apiService.pushNewPerson(person)
        }
    }

    suspend fun pushAndSaveTraining(training: Training){
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


    suspend fun getSignedTrainings(): Training {
        return apiService.loadSignedTrainingResponse()
    }


}
