package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = false)
    val username: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "name")
    val name: String
)
