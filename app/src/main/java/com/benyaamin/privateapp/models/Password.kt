package com.benyaamin.privateapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Password(
    val title: String,
    val username: String,
    val password: String,
    val type: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}