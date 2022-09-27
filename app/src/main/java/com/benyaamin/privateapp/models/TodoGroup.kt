package com.benyaamin.privateapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)
