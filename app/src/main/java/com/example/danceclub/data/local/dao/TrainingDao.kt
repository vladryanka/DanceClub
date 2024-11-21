package com.example.danceclub.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Training
import com.example.danceclub.data.model.TrainingSign
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Query("SELECT * FROM training")
    fun getTrainings(): Flow<List<Training>>

    @Query("SELECT * FROM training WHERE id = :id LIMIT 1")
    fun getById(id: String): Training?

    @Query("SELECT * FROM training")
    suspend fun getTrainingsSync(): List<Training>

    @Insert
    fun add(training: Training)


    // TODO: тут не все, что умеет делать секция
}