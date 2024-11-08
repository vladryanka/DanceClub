package com.example.danceclub.ui.screens.auth.sign_up

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.remote.DanceRepository

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getInstance(application)
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val repository: DanceRepository = DanceRepository(personDao, trainingDao)

    fun findPerson(phone:String): Person? {
        return personDao.searchPerson(phone)
    }

    private suspend fun fetchAndStorePersons(token: String) {
        repository.fetchAndSavePersons(token)
    }

    suspend fun savePerson(
        name: String,
        surname: String,
        patronimic: String,
        age: Int,
        phone: String, password: String
    ): Pair<Boolean, String> {
        val response = repository.register(
            name,
            surname,
            patronimic,
            age,
            phone, password
        )
        if (response.first == null) {
            val person = response.second
            person?.let {
                val id = it.id
                personDao.add(Person(id, name, surname, patronimic, age, phone, "nothing"))
            }

            fetchAndStorePersons(repository.token.accessToken)
            return Pair(true, repository.token.accessToken)
        } else return Pair(false, response.first!!)
    }

}