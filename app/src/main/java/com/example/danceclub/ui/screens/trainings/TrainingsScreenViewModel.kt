package com.example.danceclub.ui.screens.trainings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.local.dao.TrainingSignDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Training
import com.example.danceclub.data.remote.RepositoryProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class TrainingsScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val trainingSignDao: TrainingSignDao =
        AppDatabase.getInstance(application).trainingSignsDao()
    private val repository = RepositoryProvider.getRepository()
    private val _trainings: MutableLiveData<List<Training>> = MutableLiveData(emptyList())
    val trainings: LiveData<List<Training>> get() = _trainings
    private val _currentTraining: MutableLiveData<Training> = MutableLiveData()
    val currentTraining: LiveData<Training> get() = _currentTraining

    init {
        getTraining()
        Log.d("Doing", LocalDate.now().monthValue.toString())
        currentMonthTrainings(LocalDate.now().monthValue)
    }

    fun updateCurrentTrainings(newTraining: Training) {
        _currentTraining.postValue(newTraining)
        Log.d("Doing", _currentTraining.value.toString())
    }

    private fun getTraining() {
        viewModelScope.launch {
            val trainingList = trainingDao.getTrainingsSync()
            _trainings.postValue(trainingList)
            if (trainingList.isNotEmpty()) {
                _currentTraining.postValue(trainingList[0])
            }
        }
    }

    fun getCurrentPerson(): Person = repository.currentPerson

    suspend fun singInTraining(person: String): String? {
        return repository.postSign(currentTraining.value!!, person)
    }

    suspend fun isSignedIn(trainingId: String): Boolean {
        return withContext(Dispatchers.IO) {
            val result = trainingSignDao.getById(trainingId) != null
            return@withContext result
        }
    }

    fun currentMonthTrainings(month: Int): List<Training>? {
        // doesn't work correctly
        var trainingList: List<Training>? = null
        viewModelScope.launch {
            trainings.asFlow().collect {
                trainingList = it
                Log.d("Doing", "trainingList in currentMonthTrainings in flow: $trainingList")
            }
        }
        val trainingWithMonth: MutableList<Training> = mutableListOf()
        if (month == 0) {
            return trainingList
        }
        if (trainingList != null) {
            for (i in trainingList) {
                if (i.date.monthValue == month)
                    trainingWithMonth.add(i)
            }
        }
        return trainingWithMonth
    }

}