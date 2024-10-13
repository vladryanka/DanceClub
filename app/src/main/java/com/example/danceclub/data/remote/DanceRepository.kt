package com.example.danceclub.data.remote

import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Training

class DanceRepository (private val personDao: PersonsDao, private val trainingDao: TrainingDao) {

    suspend fun fetchAndSavePersons() {
        val personResponse = apiService.loadPersonsResponse()
        for (person in personResponse.getPersons()!!){
            if (personDao.containsPerson(person.phone)){
                personDao.add(person)
            }
        }
        val trainingResponse = apiService.loadTrainingsResponse()
        for (training in trainingResponse.trainings!!){
                trainingDao.add(training)//тут нужна какая-то проверка на повторы??
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
