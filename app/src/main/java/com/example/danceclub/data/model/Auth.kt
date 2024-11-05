package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "authorizations")
data class Auth(
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "password")
    val password: String
)
