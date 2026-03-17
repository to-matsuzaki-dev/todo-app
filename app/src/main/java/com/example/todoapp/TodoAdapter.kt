package com.example.todoapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * TodoAdapterはRecyclerViewにTodoアイテムを表示するアダプターです。
 *
 * @param onToggle チェックボックスがタップされたときに呼ばれるコールバック
 * @param onDelete 削除ボタンがタップされたときに呼ばれるコールバック
 */
class TodoAdapter(
    private val onToggle: (TodoItem) -> Unit,
    private val onDelete: (TodoItem) -> Unit
) : ListAdapter<TodoItem, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkboxCompleted)
        private val textView: TextView = itemView.findViewById(R.id.textTodo)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.buttonDelete)

        fun bind(item: TodoItem) {
            textView.text = item.text
            checkBox.isChecked = item.isCompleted

            // 完了済みの場合は打ち消し線を表示する
            if (item.isCompleted) {
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            checkBox.setOnClickListener { onToggle(item) }
            deleteButton.setOnClickListener { onDelete(item) }
        }
    }

    /** DiffUtilを使って差分を効率的に計算します。 */
    class TodoDiffCallback : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean =
            oldItem == newItem
    }
}
