package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "training_sign",
    primaryKeys = ["personId", "trainingId"]
)
data class TrainingSign(
    @ColumnInfo(name = "personId")
    val personId: Long, // in JSON String

    @ColumnInfo(name = "trainingId")
    val trainingId: Long // in JSON String
)




