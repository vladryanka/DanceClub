package com.example.danceclub.ui.screens.profile

import android.app.Application
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
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
import com.example.danceclub.data.model.TrainingSign
import com.example.danceclub.data.remote.RepositoryProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val trainingSignDao: TrainingSignDao =
        AppDatabase.getInstance(application).trainingSignsDao()
    private val repository = RepositoryProvider.getRepository()
    private val _trainings: MutableLiveData<List<Training>> = MutableLiveData(emptyList())
    private val _trainingSigns: MutableLiveData<List<TrainingSign>> = MutableLiveData(emptyList())
    private val _savedImage: MutableLiveData<Boolean> = MutableLiveData(false)
    val trainings: LiveData<List<Training>> get() = _trainings
    val trainingSign: LiveData<List<TrainingSign>> get() = _trainingSigns

    val savedImage: LiveData<Boolean> get() = _savedImage

    init {
        fetchAndStoreTrainings()
    }

    fun getToken(): Token {
        return repository.token
    }

    private fun findTrainingNameById(trainingId: String): Training? {
        return trainingDao.getById(trainingId)
    }

    suspend fun getListNamesOfTraining(): List<Training> {
        return withContext(Dispatchers.IO) {
            val list: MutableList<Training> = mutableListOf()
            Log.d("Doing", trainingSign.value.toString())
            val listOfSigned = trainingSignDao.getTrainingSignsSync()
            Log.d("Doing", "listOfSigned = $listOfSigned")
            for (i in listOfSigned) {
                val training = findTrainingNameById(i.trainingId)
                if (training != null) {
                    list.add(training)
                }
            }
            list
        }
    }

    suspend fun saveImage(image: Uri, contentResolver: ContentResolver): String? {
        val result = repository.putImage(image, contentResolver)

        if (result == "1") _savedImage.postValue(true)
        else _savedImage.postValue(false)
        result?.let {
            Log.d("Doing", "В профиле в методе saveImage $result")
        }

        return result
    }

    suspend fun getImage(): ImageBitmap? {
        return repository.getImage()
    }

    private fun fetchAndStoreTrainings() {
        viewModelScope.launch {
            repository.fetchAndSaveTrainings()
            repository.fetchAndSaveSignedTrainings()
            updateTrainings()
        }
    }

    private fun updateTrainings() {
        viewModelScope.launch {
            val trainingList = trainingDao.getTrainingsSync()
            val trainingSignList = trainingSignDao.getTrainingSignsSync()
            _trainings.postValue(trainingList)
            _trainingSigns.postValue(trainingSignList)
        }
    }

}