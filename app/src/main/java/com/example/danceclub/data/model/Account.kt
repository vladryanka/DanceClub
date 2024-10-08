<<<<<<<< HEAD:app/src/main/java/com/example/danceclub/data/model/Account.kt
package com.example.danceclub.data.model
========
package com.example.danceclub.database.account
>>>>>>>> master:app/src/main/java/com/example/danceclub/database/account/Account.kt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = false)
    var username: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "name")
    var name: String
)
