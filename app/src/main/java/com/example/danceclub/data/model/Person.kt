package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.danceclub.data.local.converters.Converters
import com.example.danceclub.data.local.serializer.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
@Entity(tableName = "persons")
data class Person(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String,
    @ColumnInfo(name = "patronimic")
    val patronimic: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @Serializable(with = LocalDateSerializer::class)
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "birth_date")
    val birth_date: LocalDate
)
