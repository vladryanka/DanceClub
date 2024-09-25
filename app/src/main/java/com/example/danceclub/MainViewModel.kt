package com.example.danceclub

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountsDao = AppDatabase.getInstance(application).accountsDao()
    private val sectionsDao: SectionDao =
        AppDatabase.getInstance(application).sectionsDao()
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)

    fun getSections(): LiveData<List<Section>> {
        return appDatabase.sectionsDao().getSections()
    }

    fun getAccounts(): LiveData<List<Account>> {
        return appDatabase.accountsDao().getAccounts()
    }

    fun findAccount(username: String): Account? {
        return accountDao.searchAccount(username)
    }

    fun saveSection(section: Section) {
        sectionsDao.add(section)
    }


    fun saveAccount(account: Account) {
        Log.d("Doing", "Пришли в saveAccount")
        accountDao.add(account)
    }
    //не хватает функционала удаления записи на секцию у аккаунта
    //и связи между аккаунтом и секцией
}