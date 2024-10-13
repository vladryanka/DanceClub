package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String,
    @ColumnInfo(name = "patronimic")
    val patronimic: String,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "phone")
    val phone: String
)
