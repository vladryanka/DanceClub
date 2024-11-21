package com.example.danceclub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthDao {

    @Query("SELECT * FROM persons")
    fun getAuth(): Flow<List<Person>>

    @Insert
    fun add(person: Person)

    @Query("SELECT * FROM persons")
    suspend fun getPersonsSync(): List<Person>

    @Query("SELECT * FROM persons WHERE phone LIKE :phone")
    fun searchPerson(phone: String): Person?

    // TODO: replacePerson()


}