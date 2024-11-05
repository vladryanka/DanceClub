package com.example.danceclub.ui.screens.auth.sing_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Token
import com.example.danceclub.data.remote.DanceRepository
import okhttp3.Response

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getInstance(application)
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val repository: DanceRepository = DanceRepository(personDao,trainingDao)

    fun findPerson(phone: String): Person? {
        return personDao.searchPerson(phone)
    }
    suspend fun fetchAndStorePersons(token:String) {
        repository.fetchAndSavePersons(token)
    }
    fun saveTokenInRepository(token: Token){
        repository.token = token
    }

    suspend fun login(phone:String, password:String): Response? {
        val token = repository.login(phone, password)
        //if (token.isSuccessful) saveTokenInRepository(token.body.toString()) // передать Token
        //else return null
        //fetchAndStorePersons(token.body) // TODO() получить accessToken
        return token
    }

}