<<<<<<<< HEAD:app/src/main/java/com/example/danceclub/data/local/dao/SectionDao.kt
package com.example.danceclub.data.local.dao
========
package com.example.danceclub.database.section
>>>>>>>> master:app/src/main/java/com/example/danceclub/database/section/SectionDao.kt

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.danceclub.data.model.Section

@Dao
interface SectionDao { // тут не все, что умеет делать секция
    @Query("SELECT * FROM sections")
    fun getSections(): LiveData<List<Section>>

    @Insert
    fun add(section: Section)
}