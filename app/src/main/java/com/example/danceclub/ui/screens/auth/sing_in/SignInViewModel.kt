package com.example.danceclub.ui.screens.auth.sing_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.AppDatabase

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()

    fun findPerson(phone: String): Person? {
        return personDao.searchPerson(phone)
    }

}