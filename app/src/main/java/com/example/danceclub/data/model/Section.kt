package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "sections")
data class Section(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "info")
    val info: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "isFree")
    val isFree: Boolean,
    @ColumnInfo(name = "teacher")
    val teacher: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "time")
    val time: LocalTime,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "class_space")
    val space:Int
)
