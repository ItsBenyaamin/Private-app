package com.benyaamin.privateapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val isFavorite: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
