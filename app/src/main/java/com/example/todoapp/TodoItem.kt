package com.example.todoapp

import java.util.concurrent.atomic.AtomicLong

/**
 * TodoItemはTodoリストの1件を表すデータクラスです。
 *
 * @param id      一意なID（アトミックカウンターで生成）
 * @param text    Todo本文
 * @param isCompleted 完了フラグ
 */
data class TodoItem(
    val id: Long = nextId(),
    val text: String,
    val isCompleted: Boolean = false
) {
    companion object {
        private val counter = AtomicLong(0)
        private fun nextId(): Long = counter.incrementAndGet()
    }
}
