package com.benyaamin.privateapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Password(
    var title: String,
    var username: String,
    var password: String,
    var type: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}