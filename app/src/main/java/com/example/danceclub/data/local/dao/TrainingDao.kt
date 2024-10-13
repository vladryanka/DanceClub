package com.example.danceclub.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.Training

@Dao
interface TrainingDao {

    @Query("SELECT * FROM training")
    fun getTrainings(): LiveData<List<Training>>

    @Insert
    fun add(training: Training)

    // TODO: тут не все, что умеет делать секция
}