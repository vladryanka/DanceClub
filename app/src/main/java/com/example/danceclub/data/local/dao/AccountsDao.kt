<<<<<<<< HEAD:app/src/main/java/com/example/danceclub/data/local/dao/AccountsDao.kt
package com.example.danceclub.data.local.dao
========
package com.example.danceclub.database.account
>>>>>>>> master:app/src/main/java/com/example/danceclub/database/account/AccountsDao.kt

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.Account

@Dao
interface AccountsDao {
    @Query("SELECT * FROM accounts")
    fun getAccounts(): LiveData<List<Account>>

    @Insert
    fun add(account: Account)
    @Query("SELECT * FROM accounts WHERE username LIKE :searchQuery")
    fun searchAccount(searchQuery: String): Account?

}