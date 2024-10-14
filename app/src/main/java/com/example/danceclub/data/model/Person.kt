package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
@Entity(tableName = "persons")
data class Person(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name")
    val name: String,
    /*@ColumnInfo(name = "password")
    val password: String,*/
    @ColumnInfo(name = "surname")
    val surname: String,
    @ColumnInfo(name = "patronimic")
    val patronimic: String,
    @ColumnInfo(name = "age")
    val age: Int,
    @ColumnInfo(name = "phone")
    val phone: String
)
