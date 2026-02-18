# TodoApp アーキテクチャ設計書

## システム概要

本アプリケーションは、MVVMアーキテクチャパターンを採用したシンプルなToDoアプリケーションです。

## コンポーネント図

```
┌─────────────────────────────────────────────────────────────┐
│                         Presentation Layer                   │
│  ┌──────────────────────────────────────────────────────┐  │
│  │              MainActivity (Compose UI)               │  │
│  │  - TodoScreen: メイン画面                            │  │
│  │  - TaskItem: タスク表示カード                        │  │
│  │  - TextField: 入力フィールド                         │  │
│  │  - LazyColumn: タスクリスト                          │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────────┐
│                      ViewModel Layer                         │
│  ┌──────────────────────────────────────────────────────┐  │
│  │                  TaskViewModel                       │  │
│  │  - tasks: StateFlow<List<Task>>                     │  │
│  │  - addTask(title: String)                           │  │
│  │  - deleteTask(task: Task)                           │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────────┐
│                      Repository Layer                        │
│  ┌──────────────────────────────────────────────────────┐  │
│  │                 TaskRepository                       │  │
│  │  - allTasks: Flow<List<Task>>                       │  │
│  │  - insertTask(task: Task)                           │  │
│  │  - deleteTask(task: Task)                           │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                            ↕
┌─────────────────────────────────────────────────────────────┐
│                        Data Layer                            │
│  ┌──────────────────────────────────────────────────────┐  │
│  │                   AppDatabase                        │  │
│  │  ┌───────────────────────────────────────────────┐  │  │
│  │  │              TaskDao                          │  │  │
│  │  │  - getAllTasks(): Flow<List<Task>>           │  │  │
│  │  │  - insertTask(task: Task)                    │  │  │
│  │  │  - deleteTask(task: Task)                    │  │  │
│  │  └───────────────────────────────────────────────┘  │  │
│  │  ┌───────────────────────────────────────────────┐  │  │
│  │  │           Task Entity                         │  │  │
│  │  │  - id: Int                                    │  │  │
│  │  │  - title: String                              │  │  │
│  │  │  - createdAt: Long                            │  │  │
│  │  └───────────────────────────────────────────────┘  │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## データフロー

### タスク追加フロー
```
User Input
   ↓
TextField onChange
   ↓
FAB Click
   ↓
viewModel.addTask(title)
   ↓
repository.insertTask(Task)
   ↓
taskDao.insertTask(task)
   ↓
Room Database
   ↓
Flow emission
   ↓
UI Update
```

### タスク削除フロー
```
Delete Button Click
   ↓
viewModel.deleteTask(task)
   ↓
repository.deleteTask(task)
   ↓
taskDao.deleteTask(task)
   ↓
Room Database
   ↓
Flow emission
   ↓
UI Update
```

### タスク表示フロー
```
App Launch
   ↓
ViewModel Init
   ↓
repository.allTasks (Flow)
   ↓
taskDao.getAllTasks()
   ↓
Room Database Query
   ↓
Flow<List<Task>>
   ↓
StateFlow in ViewModel
   ↓
collectAsState() in Compose
   ↓
LazyColumn rendering
```

## 状態管理

- **StateFlow**: ViewModelからUIへの一方向データフロー
- **Flow**: Roomからの非同期データストリーム
- **remember/mutableStateOf**: Compose内のローカル状態管理

## 非同期処理

- **Coroutines**: 非同期データベース操作
- **viewModelScope**: ViewModelのライフサイクルに紐づくCoroutineスコープ
- **suspend関数**: データベース書き込み操作

## UI Components

### TodoScreen
- Scaffold: アプリケーションの基本構造
- TopAppBar: タイトルバー
- FloatingActionButton: タスク追加ボタン
- OutlinedTextField: タスク入力フィールド
- LazyColumn: タスクリスト（仮想化されたリスト）

### TaskItem
- Card: タスクカードコンテナ
- Text: タスクタイトル表示
- IconButton: 削除ボタン
- Icon: 削除アイコン

## Theme
- Material Design 3
- Dynamic Color (Android 12+)
- Light/Dark Theme対応
