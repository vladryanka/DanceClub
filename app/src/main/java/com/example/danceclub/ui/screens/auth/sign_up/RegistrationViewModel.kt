package com.example.danceclub.ui.screens.auth.sign_up

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.model.Account
import com.example.danceclub.data.local.dao.AccountsDao
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.SectionDao

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountsDao = AppDatabase.getInstance(application).accountsDao()
    private val sectionsDao: SectionDao = AppDatabase.getInstance(application).sectionsDao()
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)

    fun findAccount(username: String): Account? {
        return accountDao.searchAccount(username)
    }

    fun saveAccount(account: Account) {
        Log.d("Doing", "Пришли в saveAccount")
        accountDao.add(account)
    }

    // TODO:
    //не хватает функционала удаления записи на секцию у аккаунта
    //и связи между аккаунтом и секцией
}