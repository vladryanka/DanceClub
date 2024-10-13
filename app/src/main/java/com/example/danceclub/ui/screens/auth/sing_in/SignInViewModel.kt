package com.example.danceclub.ui.screens.auth.sing_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.model.Account
import com.example.danceclub.data.local.dao.AccountsDao
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.TrainingDao

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountsDao = AppDatabase.getInstance(application).accountsDao()
    private val sectionsDao: TrainingDao = AppDatabase.getInstance(application).trainingsDao()
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)
//    private val apiFactory: ApiFactory = ApiFactory()

    fun findAccount(username: String): Account? {
        return accountDao.searchAccount(username)
    }

    /*fun getSections(): LiveData<List<Section>> {
        return appDatabase.sectionsDao().getSections()
    }

    fun getAccounts(): LiveData<List<Account>> {
        return appDatabase.accountsDao().getAccounts()
    }

    fun saveSection(section: Section) {
        sectionsDao.add(section)
    }

     */

}