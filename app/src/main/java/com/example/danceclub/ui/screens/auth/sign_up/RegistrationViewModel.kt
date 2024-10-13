package com.example.danceclub.ui.screens.auth.sign_up

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val sectionsDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)
//    private val apiFactory: ApiFactory = ApiFactory()

    fun findPerson(id: String): Person? {
        return personDao.searchPerson(id)
    }

    fun savePerson(person: Person) {
        Log.d("Doing", "Пришли в saveAccount")
        personDao.add(person)
    }

    // TODO:
    //не хватает функционала удаления записи на секцию у аккаунта
    //и связи между аккаунтом и секцией
}