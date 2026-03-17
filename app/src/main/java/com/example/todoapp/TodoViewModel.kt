package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * TodoViewModelはTodoリストの状態を管理するViewModelです。
 * LiveDataを使って画面にデータを通知します。
 */
class TodoViewModel : ViewModel() {

    private val _todoList = MutableLiveData<List<TodoItem>>(emptyList())

    /** 全Todoアイテムのリスト（読み取り専用） */
    val todoList: LiveData<List<TodoItem>> get() = _todoList

    /** 新しいTodoを追加します。テキストが空の場合は何もしません。 */
    fun addTodo(text: String) {
        if (text.isBlank()) return
        _todoList.value = _todoList.value.orEmpty() + TodoItem(text = text)
    }

    /** 指定したTodoの完了状態を切り替えます。 */
    fun toggleComplete(item: TodoItem) {
        _todoList.value = _todoList.value.orEmpty().map { todo ->
            if (todo.id == item.id) todo.copy(isCompleted = !todo.isCompleted) else todo
        }
    }

    /** 指定したTodoを削除します。 */
    fun deleteTodo(item: TodoItem) {
        _todoList.value = _todoList.value.orEmpty().filter { it.id != item.id }
    }
}
