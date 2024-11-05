package com.example.danceclub.ui.screens.auth.sign_up

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.remote.DanceRepository
import kotlinx.coroutines.flow.Flow

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val repository: DanceRepository = DanceRepository(personDao,trainingDao)

    fun getPersons(): Flow<List<Person>> {
        return personDao.getPersons()
    }
    suspend fun fetchAndStorePersons() {
        repository.fetchAndSavePersons()
    }
    suspend fun savePerson(person: Person) {
        repository.pushAndSavePersons(person)
        personDao.add(person)
    }
}