package com.example.danceclub

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database


@Database(entities = [Account::class, Section::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun accountsDao(): AccountsDao
    abstract fun sectionsDao(): SectionDao


    companion object{
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "leti_coin.db"
            ).build()
        }
    }
}