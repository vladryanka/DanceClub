package com.example.danceclub.ui.screens.auth

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.remote.DanceRepository
import kotlinx.coroutines.launch

class AuthViewModel (application: Application) : AndroidViewModel(application) {
    private val personDao: PersonsDao = AppDatabase.getInstance(application).personsDao()
    private val trainingDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val repository: DanceRepository = DanceRepository(personDao,trainingDao)
    private val _persons: MutableLiveData<List<Person>> = MutableLiveData(emptyList())
    val persons: LiveData<List<Person>> get() = _persons
    private val _personWithPhone: MutableLiveData<List<Person>> = MutableLiveData(emptyList())
    val personWithPhone: LiveData<List<Person>> get() = _personWithPhone

    init {
        fetchAndStorePersons()
    }

    private fun fetchAndStorePersons() {
        viewModelScope.launch {
            repository.fetchAndSavePersons()
            updatePersons()
        }
    }
    private fun updatePersons() {
        viewModelScope.launch {
            val personList = personDao.getPersonsSync()
            _persons.postValue(personList)
        }
    }
   /* fun getPersonWithPhone(phone: String){
        viewModelScope.launch {
            val person = personDao.searchPerson(phone)
            _personWithPhone.postValue(person)
        }
    }*/
    fun savePerson(person: Person) {
        Log.d("Doing", "Пришли в savePerson")
        personDao.add(person)
    }
    fun findPerson(phone: String): Person? {
        return personDao.searchPerson(phone)
    }

}