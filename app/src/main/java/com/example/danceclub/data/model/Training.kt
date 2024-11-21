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

@Entity(tableName = "training")
@Serializable
data class Training(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @Serializable(with = LocalDateSerializer::class)
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "status")
    val status: Int = 0,
    @ColumnInfo(name = "space")
    val space: Int = 0,
    @ColumnInfo(name = "freeSpace")
    val freeSpace: Int = 0,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "trainerName")
    val trainerName: String,
    @ColumnInfo(name = "trainerDescriptions")
    val trainerDescriptions: String
)
