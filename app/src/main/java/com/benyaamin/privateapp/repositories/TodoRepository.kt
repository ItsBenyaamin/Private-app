package com.benyaamin.privateapp.repositories

import com.benyaamin.privateapp.models.Todo
import com.benyaamin.privateapp.models.TodoGroup
import com.benyaamin.privateapp.persistence.TodoDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
) {

    suspend fun getTodoGroups(): List<TodoGroup> {
        return withContext(IO) {
            val groups = todoDao.getTodoGroups()
            groups.forEach {
                val todos = todoDao.getTodos(it.id)
                it.todoList.addAll(todos)
            }
            groups
        }
    }

    suspend fun addGroup(group: TodoGroup): Flow<Boolean> = flow {
        val groupExist = withContext(IO) {
            todoDao.isGroupExist(group.title)
        }

        if (groupExist) {
            emit(false)
            return@flow
        }
        todoDao.insertTodoGroup(group)
        emit(true)
    }

    suspend fun addTodo(todo: Todo) {
        withContext(IO) {
            todoDao.insertTodo(todo)
        }
    }

    suspend fun deleteGroup(group:TodoGroup) {
        withContext(IO) {
            todoDao.deleteTodoGroup(group)
        }
    }

    suspend fun deleteTodo(todo: Todo) {
        withContext(IO) {
            todoDao.deleteTodo(todo)
        }
    }

    suspend fun updateGroup(group: TodoGroup): Flow<Boolean> = flow {
        val groupExist = withContext(IO) {
            todoDao.isGroupExist(group.title)
        }

        if (groupExist) {
            emit(false)
            return@flow
        }
        todoDao.updateTodoGroup(group)
        emit(true)
    }

    suspend fun updateTodo(todo:Todo) {
        withContext(IO) {
            todoDao.updateTodo(todo)
        }
    }

}