package com.benyaamin.privateapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.benyaamin.privateapp.models.Password

@Database(
    entities = [Password::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}