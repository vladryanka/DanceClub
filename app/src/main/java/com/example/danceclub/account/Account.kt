package com.example.danceclub.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = false)
    var username: String,
    @ColumnInfo(name = "password")
    var password:String,
    @ColumnInfo(name = "name")
    var name: String

)
