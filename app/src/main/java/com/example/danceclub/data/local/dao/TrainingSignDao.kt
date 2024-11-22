package com.example.danceclub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.TrainingSign
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingSignDao {

    @Query("SELECT * FROM training_sign")
    fun getTrainings(): Flow<List<TrainingSign>>

    @Query("SELECT * FROM training_sign WHERE trainingId = :newTrainingId")
    suspend fun getById(newTrainingId: String): TrainingSign?

    @Query("SELECT * FROM training_sign")
    suspend fun getTrainingSignsSync(): List<TrainingSign>

    @Insert
    fun add(trainingSign: TrainingSign)

}