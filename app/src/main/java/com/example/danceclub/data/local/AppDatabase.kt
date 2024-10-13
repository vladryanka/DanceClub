package com.example.danceclub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.danceclub.data.local.AppDatabase.Companion.DATABASE_VERSION
import com.example.danceclub.data.local.dao.PersonsDao
import com.example.danceclub.data.local.dao.TrainingDao
import com.example.danceclub.data.model.Person
import com.example.danceclub.data.model.Training
import com.example.danceclub.data.local.converters.Converters
import com.example.danceclub.data.local.dao.TrainingSignDao
import com.example.danceclub.data.model.TrainingSign

@Database(
    entities = [Person::class, Training::class, TrainingSign::class],
    version = DATABASE_VERSION
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personsDao(): PersonsDao
    abstract fun trainingsDao(): TrainingDao
    abstract fun trainingSignsDao(): TrainingSignDao


    companion object {

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "UserPlaceDatabase"

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}