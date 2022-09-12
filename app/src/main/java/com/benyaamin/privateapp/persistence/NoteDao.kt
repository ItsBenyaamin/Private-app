package com.benyaamin.privateapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.benyaamin.privateapp.models.Note

@Dao
interface NoteDao {
    @Query("select * from note")
    suspend fun getNotes(): List<Note>

    @Query("select * from note where title like :query")
    suspend fun search(query: String): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}