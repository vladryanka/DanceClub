package com.example.danceclub.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.UUID

@Entity(
    tableName = "training_sign",
    primaryKeys = ["personId", "trainingId"]
)
data class TrainingSign(
    @ColumnInfo(name = "personId")
    val personId: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "trainingId")
    val trainingId: String = UUID.randomUUID().toString()
)




