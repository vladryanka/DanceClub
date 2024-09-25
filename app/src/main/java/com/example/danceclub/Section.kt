package com.example.danceclub

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections")
data class Section(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "info")
    var info:String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "isFree")
    var isFree:Boolean
)
