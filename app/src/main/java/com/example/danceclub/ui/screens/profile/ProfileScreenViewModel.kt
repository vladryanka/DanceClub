package com.example.danceclub.ui.screens.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.remote.DanceRepository

class ProfileScreenViewModel (application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val repository: DanceRepository = DanceRepository(personDao,trainingDao)

    fun saveImage(image:Uri, person:Person){
        val personWithImage = personDao.searchPerson(person.phone)
        //personWithImage.image = image
        //personDao.replacePerson()
        //TODO
    }
}