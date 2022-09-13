package com.benyaamin.privateapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    var title: String,
    var content: String,
    var isFavorite: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
