package com.example.danceclub.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.danceclub.data.local.AppDatabase.Companion.DATABASE_VERSION
import com.example.danceclub.data.local.dao.AccountsDao
import com.example.danceclub.data.local.dao.SectionDao
import com.example.danceclub.data.model.Account
import com.example.danceclub.data.model.Section

@Database(
    entities = [Account::class, Section::class],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountsDao(): AccountsDao
    abstract fun sectionsDao(): SectionDao

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