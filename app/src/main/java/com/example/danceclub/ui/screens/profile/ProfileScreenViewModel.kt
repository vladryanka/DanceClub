package com.example.danceclub.ui.screens.profile

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.local.dao.TrainingSignDao
import com.example.danceclub.data.model.Token
import com.example.danceclub.data.model.Training
import com.example.danceclub.data.remote.RepositoryProvider
import kotlinx.coroutines.launch

class ProfileScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val trainingSignDao: TrainingSignDao =
        AppDatabase.getInstance(application).trainingSignsDao()
    private val repository = RepositoryProvider.getRepository()
    private val _trainings: MutableLiveData<List<Training>> = MutableLiveData(emptyList())
    private val _savedImage: MutableLiveData<Boolean> = MutableLiveData(false)
    val trainings: LiveData<List<Training>> get() = _trainings
    val savedImage: LiveData<Boolean> get() = _savedImage

    init {
        fetchAndStoreTrainings()
    }

    fun getToken(): Token {
        return repository.token
    }

    suspend fun saveImage(image: Uri, contentResolver: ContentResolver) {
        Log.d("Doing", "Пришли в saveImage")

        val result = repository.putImage(image, contentResolver)

        if (result == "1") _savedImage.postValue(true)
        else _savedImage.postValue(false)
        result?.let {
            Log.d("Doing", result)
        }

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

    fun getTrainingSign() {
        repository.getSignedTrainings()
    }

}