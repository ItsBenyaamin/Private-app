package com.benyaamin.privateapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.benyaamin.privateapp.models.Todo
import com.benyaamin.privateapp.models.TodoGroup

@Dao
interface TodoDao {
    @Query("select * from todogroup")
    suspend fun getTodoGroups(): List<TodoGroup>

    @Query("select exists(select title from todogroup where title = :title)")
    suspend fun isGroupExist(title: String): Boolean

    @Insert
    suspend fun insertTodoGroup(group: TodoGroup)

    @Delete
    suspend fun deleteTodoGroup(group: TodoGroup)

    @Update
    suspend fun updateTodoGroup(group: TodoGroup)


    @Query("select * from todo where groupId = :groupId")
    suspend fun getTodos(groupId: Int): List<Todo>

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)
}