package com.benyaamin.privateapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.benyaamin.privateapp.models.Password

@Dao
interface PasswordDao {
    @Query("select * from password")
    suspend fun getPasswords(): List<Password>

    @Query("select * from password where title like :query")
    suspend fun search(query: String): List<Password>

    @Query("select * from password where id = :passwordId")
    suspend fun getPassword(passwordId: Int): Password

    @Insert
    suspend fun insert(password: Password)

    @Update
    suspend fun update(password: Password)

    @Delete
    suspend fun delete(password: Password)
}