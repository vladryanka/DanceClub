package com.example.danceclub.ui.screens.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.remote.DanceRepository

class ProfileViewModel(application: Application): AndroidViewModel(application) {
        private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
        private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
        private val repository: DanceRepository = DanceRepository(personDao,trainingDao)

        fun getPersons(): LiveData<List<Person>> {
            return personDao.getPersons()
        }
        suspend fun fetchAndStorePersons() {
            repository.fetchAndSavePersons()
        }

        fun savePerson(person: Person) {
            Log.d("Doing", "Пришли в savePerson")
            personDao.add(person)
        }

}