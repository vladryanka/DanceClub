<<<<<<<< HEAD:app/src/main/java/com/example/danceclub/ui/screens/auth/sign_up/RegistrationViewModel.kt
package com.example.danceclub.ui.screens.auth.sign_up
========
package com.example.danceclub.viewModel
>>>>>>>> master:app/src/main/java/com/example/danceclub/viewModel/RegistrationViewModel.kt

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
<<<<<<<< HEAD:app/src/main/java/com/example/danceclub/ui/screens/auth/sign_up/RegistrationViewModel.kt
import com.example.danceclub.data.model.Account
import com.example.danceclub.data.local.dao.AccountsDao
import com.example.danceclub.data.local.AppDatabase
import com.example.danceclub.data.local.dao.SectionDao
========
import com.example.danceclub.API.ApiFactory
import com.example.danceclub.database.AppDatabase
import com.example.danceclub.database.account.Account
import com.example.danceclub.database.account.AccountsDao
import com.example.danceclub.database.section.SectionDao
>>>>>>>> master:app/src/main/java/com/example/danceclub/viewModel/RegistrationViewModel.kt

class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    private val accountDao: AccountsDao = AppDatabase.getInstance(application).accountsDao()
    private val sectionsDao: SectionDao = AppDatabase.getInstance(application).sectionsDao()
    private val appDatabase: AppDatabase = AppDatabase.getInstance(application)
    private val apiFactory: ApiFactory = ApiFactory()

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