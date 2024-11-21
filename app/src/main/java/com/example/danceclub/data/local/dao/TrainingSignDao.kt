package com.example.danceclub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.Training
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingSignDao {

    @Query("SELECT * FROM training")
    fun getSignedTrainings(): Flow<List<Training>>

    @Query("SELECT * FROM training WHERE id = :id LIMIT 1")
    fun getById(id: String): Training?

    @Query("SELECT * FROM training")
    suspend fun getTrainingsSync(): List<Training>

    @Insert
    fun add(trainingId: String, personId: String)

}