package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.TodoAppTheme

/**
 * MainActivityはJetpack Composeを使ったアプリのエントリーポイントです。
 */
class MainActivity : ComponentActivity() {

    private val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoScreen(viewModel = viewModel)
                }
            }
        }
    }
}

/** Todoアプリのメイン画面です。入力欄とリストを表示します。 */
@Composable
fun TodoScreen(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Todo App",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TodoInput(onAdd = { text -> viewModel.addTodo(text) })

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(todoList, key = { it.id }) { item ->
                TodoItemRow(
                    item = item,
                    onToggle = { viewModel.toggleComplete(item) },
                    onDelete = { viewModel.deleteTodo(item) }
                )
            }
        }
    }
}

/** Todo入力フォームです。テキストフィールドと追加ボタンを表示します。 */
@Composable
fun TodoInput(onAdd: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Todoを入力してください") },
            modifier = Modifier.weight(1f),
            singleLine = true
        )
        Button(
            onClick = {
                onAdd(text)
                text = ""
            }
        ) {
            Text("追加")
        }
    }
}

/** Todoアイテム1件の行です。チェックボックス・テキスト・削除ボタンを表示します。 */
@Composable
fun TodoItemRow(
    item: TodoItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = item.isCompleted, onCheckedChange = { onToggle() })

        Text(
            text = item.text,
            modifier = Modifier.weight(1f),
            style = if (item.isCompleted) {
                MaterialTheme.typography.bodyLarge.copy(
                    textDecoration = TextDecoration.LineThrough
                )
            } else {
                MaterialTheme.typography.bodyLarge
            }
        )

        IconButton(onClick = onDelete) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "削除")
        }
    }
}
