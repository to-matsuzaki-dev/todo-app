package com.example.todoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MainActivityはアプリのメイン画面です。
 * Todo入力フォームとリスト表示を担当します。
 */
class MainActivity : AppCompatActivity() {

    private val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextTodo)
        val addButton = findViewById<Button>(R.id.buttonAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTodos)

        // アダプターを作成してRecyclerViewに設定する
        val adapter = TodoAdapter(
            onToggle = { item -> viewModel.toggleComplete(item) },
            onDelete = { item -> viewModel.deleteTodo(item) }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // ViewModelのLiveDataを監視してリストを更新する
        viewModel.todoList.observe(this) { todos ->
            adapter.submitList(todos)
        }

        // 追加ボタンのクリックでTodoを追加する
        addButton.setOnClickListener {
            val text = editText.text.toString().trim()
            viewModel.addTodo(text)
            editText.text.clear()
        }
    }
}
