package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_sign")
data class TrainingSign(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "personId")
    val personId:String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trainingId")
    val trainingId:String
)



