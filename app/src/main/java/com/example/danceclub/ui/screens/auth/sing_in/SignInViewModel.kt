package com.example.danceclub.ui.screens.auth.sing_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.local.dao.TrainingSignDao
import com.example.danceclub.data.remote.DanceRepository

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val appDatabase = AppDatabase.getInstance(application)
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val trainingSignDao: TrainingSignDao = AppDatabase.getInstance(application).trainingSignsDao()
    private val repository: DanceRepository = DanceRepository(personDao, trainingDao, trainingSignDao)

    fun findPerson(phone: String): Person? {
        return personDao.searchPerson(phone)
    }

    private suspend fun fetchAndStorePersons(token: String) {
        repository.fetchAndSavePersons(token)
    }

    suspend fun login(phone: String, password: String): Pair<Boolean,String> {
        val result = repository.login(phone, password)
        if (result == null) {
            fetchAndStorePersons(repository.token.accessToken)
            return Pair(true,repository.token.accessToken)
        } else return Pair(false,result)
    }
}