package com.example.danceclub

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.danceclub.account.Account
import com.example.danceclub.account.AccountsDao
import com.example.danceclub.section.Section
import com.example.danceclub.section.SectionDao

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
    //не хватает функционала удаления записи на секцию у аккаунта
    //и связи между аккаунтом и секцией
}