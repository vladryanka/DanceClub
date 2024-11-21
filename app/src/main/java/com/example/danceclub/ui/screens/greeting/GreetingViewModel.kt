package com.example.danceclub.ui.screens.greeting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.local.dao.TrainingSignDao
import com.example.danceclub.data.remote.RepositoryProvider

class GreetingViewModel(application: Application) : AndroidViewModel(application) {
    private val appDatabase = AppDatabase.getInstance(application)
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val trainingSignDao: TrainingSignDao = AppDatabase.getInstance(application).trainingSignsDao()
    init {
        RepositoryProvider.initialize(personDao, trainingDao, trainingSignDao)
    }
}