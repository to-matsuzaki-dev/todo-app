# Todo App

シンプルなAndroid Todoアプリです。

## 機能

- ✅ Todoアイテムの**追加**（テキスト入力 + 追加ボタン）
- ✅ Todoアイテムの**完了チェック**（チェックボックスで完了/未完了を切り替え）
- ✅ Todoアイテムの**削除**
- ✅ Todoリストの**一覧表示**

## スクリーンショット

アプリを起動するとTodo入力フォームとリストが表示されます。

## 技術スタック

| 項目 | 内容 |
|------|------|
| 言語 | Kotlin |
| 最小SDK | API 24 (Android 7.0) |
| ターゲットSDK | API 34 (Android 14) |
| UI | Jetpack Compose + LazyColumn |
| アーキテクチャ | MVVM（ViewModel + StateFlow） |
| ビルドツール | Gradle |

## プロジェクト構成

```
app/
  build.gradle.kts
  src/main/
    AndroidManifest.xml
    java/com/example/todoapp/
      MainActivity.kt           # メイン画面（Compose UI）
      TodoViewModel.kt          # ViewModel（StateFlowでデータ管理）
      TodoItem.kt               # Todoアイテムのデータクラス
      ui/theme/
        Theme.kt                # Material3テーマ
build.gradle.kts
settings.gradle.kts
```

## ローカルでのビルド方法

### 必要なもの

- JDK 17以上
- Android SDK（ANDROID_HOME環境変数を設定してください）

### ビルド手順

```bash
# リポジトリをクローン
git clone https://github.com/to-matsuzaki-dev/todo-app.git
cd todo-app

# Gradleラッパーを生成（初回のみ）
gradle wrapper --gradle-version 8.2.1

# デバッグAPKをビルド
./gradlew assembleDebug

# ビルドされたAPKの場所
# app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions による自動ビルド

`main` ブランチへのプッシュ時に自動でビルドが実行され、APKが **GitHub Releases** にアップロードされます。

1. PRをマージする
2. GitHub Actionsが自動でAPKをビルド
3. **Releases** ページからAPKをダウンロード
4. Androidスマートフォンにインストール

## インストール方法

1. [Releases](../../releases) ページを開く
2. 最新リリースから `app-debug.apk` をダウンロード
3. Androidスマートフォンで「提供元不明のアプリ」を許可してインストール