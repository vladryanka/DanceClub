package com.example.danceclub.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.TrainingSign

@Dao
interface TrainingSignDao {

    @Query("SELECT * FROM training_sign")
    fun getTrainingSign(): LiveData<List<TrainingSign>>

    @Insert
    fun add(trainingSign: TrainingSign)

    // TODO: тут не все, что умеет делать запись на секцию

}