package com.example.danceclub.data.remote

import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Training
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DanceRepository(private val personDao: PersonsDao, private val trainingDao: TrainingDao) {

    suspend fun fetchAndSavePersons() {
        val personResponse = apiService.loadPersonsResponse()
        withContext(Dispatchers.IO) {
            for (person in personResponse.getPersons()!!) {
                if (personDao.containsPerson(person.phone)) {
                    personDao.add(person)
                }
            }
        }
    }

    suspend fun fetchAndSaveTrainings() {
        val trainingResponse = apiService.loadTrainingsResponse()
        withContext(Dispatchers.IO) {
            for (training in trainingResponse.trainings!!) {
                val existingTraining = trainingDao.getById(training.id)
                if (existingTraining == null) {
                    trainingDao.add(training)
                }
            }
        }
    }

    suspend fun getSelectedTraining(id: String): Training {
        return apiService.loadSelectedTrainingResponse(id)
    }

    suspend fun getSignedTrainings(): Training {
        return apiService.loadSignedTrainingResponse()
    }

    suspend fun getAllPersons(): PersonResponse {
        return apiService.loadPersonsResponse()
    }

    // TODO: добавить функции для POST-запросов
}
