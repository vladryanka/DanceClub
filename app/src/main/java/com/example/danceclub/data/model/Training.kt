package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Entity(tableName = "training")
@Serializable
data class Training(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "description")
    val description:String,
    @Contextual
    @ColumnInfo(name = "date")
    val date:LocalDate,
    @ColumnInfo(name = "status")
    val status:Boolean,
    @ColumnInfo(name = "space")
    val space:Int,
    @ColumnInfo(name = "freeSpace")
    val freeSpace:Int
)
