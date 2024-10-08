<<<<<<<< HEAD:app/src/main/java/com/example/danceclub/ui/screens/auth/sing_in/SignInViewModel.kt
package com.example.danceclub.ui.screens.auth.sing_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.data.model.Account
import com.example.danceclub.data.local.dao.AccountsDao
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.SectionDao
========
package com.example.danceclub.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.danceclub.API.ApiFactory
import com.example.danceclub.database.AppDatabase
import com.example.danceclub.database.account.Account
import com.example.danceclub.database.account.AccountsDao
import com.example.danceclub.database.section.SectionDao
>>>>>>>> master:app/src/main/java/com/example/danceclub/viewModel/AuthorizationViewModel.kt

class SignInViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountsDao = AppDatabase.getInstance(application).accountsDao()
    private val sectionsDao: SectionDao = AppDatabase.getInstance(application).sectionsDao()
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)
    private val apiFactory: ApiFactory = ApiFactory()

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