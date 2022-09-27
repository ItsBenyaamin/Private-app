package com.benyaamin.privateapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val groupId: Int,
    val done: Boolean,
    val doneAt: String,
)
