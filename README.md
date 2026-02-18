# TodoApp - Kotlin & Jetpack Compose

シンプルなToDoアプリケーション（Kotlin、Jetpack Compose、Room、MVVMアーキテクチャ）

## 機能

- タスクの追加
- タスク一覧の表示
- タスクの削除
- Roomデータベースによるローカルストレージ
- Material Design 3によるモダンなUI

## 技術スタック

- **言語**: Kotlin
- **UIフレームワーク**: Jetpack Compose
- **アーキテクチャ**: MVVM (Model-View-ViewModel)
- **データベース**: Room
- **ビルドツール**: Gradle (Kotlin DSL)

## プロジェクト構成

```
app/src/main/java/com/example/todoapp/
├── MainActivity.kt              # メインアクティビティとComposeUI
├── data/
│   ├── Task.kt                 # タスクエンティティ
│   ├── TaskDao.kt              # データアクセスオブジェクト
│   ├── AppDatabase.kt          # Roomデータベース
│   └── TaskRepository.kt       # リポジトリパターン
├── viewmodel/
│   └── TaskViewModel.kt        # ビューモデル (MVVMのVM)
└── ui/theme/
    ├── Color.kt               # カラーパレット
    ├── Theme.kt               # アプリテーマ
    └── Type.kt                # タイポグラフィ
```

## ビルド方法

### 必要な環境

- Android Studio Hedgehog | 2023.1.1以上
- JDK 17以上
- Android SDK (API 34)
- Gradle 8.2

### ビルド手順

1. Android Studioでプロジェクトを開く
2. Gradle Syncを実行
3. エミュレータまたは実機でアプリを実行

```bash
./gradlew build
./gradlew installDebug
```

## アーキテクチャの詳細

### MVVM パターン

- **Model**: `Task.kt`, `TaskDao.kt`, `AppDatabase.kt`, `TaskRepository.kt`
  - データ層の管理
  - Roomによるデータベース操作
  
- **View**: `MainActivity.kt`
  - Jetpack ComposeによるUI
  - ユーザーインタラクション
  
- **ViewModel**: `TaskViewModel.kt`
  - ビジネスロジック
  - UIとデータ層の仲介
  - StateFlowによる状態管理

### データフロー

```
User Input → Compose UI → ViewModel → Repository → Room Database
                ↑                                          ↓
                ←─────────── StateFlow ←──────────────────
```

## 使用ライブラリ

```kotlin
// Jetpack Compose
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose:1.8.1")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

// Room
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
```

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。