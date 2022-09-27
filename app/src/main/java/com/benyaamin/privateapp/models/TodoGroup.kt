package com.benyaamin.privateapp.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class TodoGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
) {
    @Ignore
    var todoList: ArrayList<Todo> = arrayListOf()
}
