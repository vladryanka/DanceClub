package com.example.danceclub.section

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SectionDao { // тут не все, что умеет делать секция
    @Query("SELECT * FROM sections")
    fun getSections(): LiveData<List<Section>>

    @Insert
    fun add(section: Section)
}