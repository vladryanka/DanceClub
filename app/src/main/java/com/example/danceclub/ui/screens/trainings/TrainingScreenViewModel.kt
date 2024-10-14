package com.example.danceclub.ui.screens.trainings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Training
import com.example.danceclub.data.remote.DanceRepository
import kotlinx.coroutines.launch

class TrainingScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val repository: DanceRepository = DanceRepository(personDao, trainingDao)
    private val _trainings: MutableLiveData<List<Training>> = MutableLiveData(emptyList())
    val trainings: LiveData<List<Training>> get() = _trainings
    private val _currentTraining: MutableLiveData<Training> = MutableLiveData()
    val currentTraining: LiveData<Training> get() = _currentTraining

    init {
        fetchAndStoreTrainings()
    }

    fun updateCurrentTrainings(newTraining: Training) {
        _currentTraining.postValue(newTraining)
    }

    private fun fetchAndStoreTrainings() {
        viewModelScope.launch {
            repository.fetchAndSaveTrainings()
            updateTrainings()
        }
    }

    private fun updateTrainings() {
        viewModelScope.launch {
            val trainingList = trainingDao.getTrainingsSync()
            _trainings.postValue(trainingList)
        }
    }
}