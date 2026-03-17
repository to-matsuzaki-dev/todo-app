package com.example.todoapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * TodoViewModelはTodoリストの状態を管理するViewModelです。
 * StateFlowを使ってCompose UIにデータを通知します。
 */
class TodoViewModel : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())

    /** 全Todoアイテムのリスト（読み取り専用） */
    val todoList: StateFlow<List<TodoItem>> = _todoList.asStateFlow()

    /** 新しいTodoを追加します。テキストが空の場合は何もしません。 */
    fun addTodo(text: String) {
        if (text.isBlank()) return
        _todoList.value = _todoList.value + TodoItem(text = text)
    }

    /** 指定したTodoの完了状態を切り替えます。 */
    fun toggleComplete(item: TodoItem) {
        _todoList.value = _todoList.value.map { todo ->
            if (todo.id == item.id) todo.copy(isCompleted = !todo.isCompleted) else todo
        }
    }

    /** 指定したTodoを削除します。 */
    fun deleteTodo(item: TodoItem) {
        _todoList.value = _todoList.value.filter { it.id != item.id }
    }
}
