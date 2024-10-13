package com.example.danceclub.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.Person

@Dao
interface PersonsDao {

    @Query("SELECT * FROM persons")
    fun getPersons(): LiveData<List<Person>>

    @Insert
    fun add(person: Person)

    @Query("SELECT * FROM persons WHERE phone LIKE :phone")
    fun searchPerson(phone: String): Person?

    @Query("SELECT * FROM persons WHERE phone LIKE :phone")
    fun containsPerson(phone: String): Boolean

}