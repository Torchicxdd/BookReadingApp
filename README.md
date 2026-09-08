# BookWorm

**Developers: Parker David, Do Hoang Hai, Smadja Eliana, Maara Vanessa Purici**

BookWorm is an Android reading app that lets you discover, download, and read public-domain books from [Project Gutenberg](https://www.gutenberg.org/). Browse the library, add books to your personal bookshelf, and read them chapter by chapter with an immersive reading mode and full-text search.

## Features

- **Library** — browse a collection of downloadable Project Gutenberg titles rendered as a book-cover grid.
- **Download & offline reading** — books are downloaded as zip files, unzipped, and parsed into structured content stored locally on your device.
- **Progress feedback** — a live progress indicator tracks downloading, unzipping, and parsing.
- **Bookshelf** — your downloaded books persist locally and appear on your bookshelf.
- **Table of contents** — a per-book chapter list lets you jump straight to any chapter.
- **Reading screen** — paginated, scrollable page display with embedded images and previous/next chapter navigation.
- **Reading mode** — tap the screen to hide all navigation chrome for an immersive, full-screen reading experience.
- **Search within a book** — find every occurrence of a word or phrase and jump directly to the matching chapter.
- **Adaptive navigation** — the app layout adapts to screen size: bottom navigation on compact screens, a navigation rail on medium screens, and a permanent navigation drawer on expanded screens (e.g. tablets).

## Tech Stack

- **Kotlin** with coroutines
- **Jetpack Compose** with Material 3
- **MVVM** architecture
- **Room** for local persistence
- **OkHttp** for network requests / file downloads
- **jsoup** for parsing book HTML content
- **Navigation Compose** for in-app navigation
- **JUnit, Mockito, MockWebServer** for unit tests
- **Compose UI tests** (`ui-test-junit4`) for instrumented tests

## Architecture

The app follows an **MVVM** architecture:

- **`ui/screens/`** — Compose UI screens (`Home`, `Library`, `Bookshelf`, `ContentTable`, `Reading`, `Search`).
- **`ui/viewmodels/`** — ViewModels exposing app state to the UI (book, chapter, paragraph, table, image, and download data).
- **`data/repositories/`** — repository layer that mediates between ViewModels and the data sources.
- **`data/entities/` + `data/daos/`** — Room entities and DAOs (`Books`, `Chapters`, `Paragraphs`, `Table`, `Image`).
- **`data/download/FileDownload.kt`** — handles downloading book zips, unzipping them, and saving content to app storage.

## Project Structure

```
app/src/main/java/com/example/bookreadingapp/
├── MainActivity.kt
├── data/
│   ├── Book.kt                 # Book model, HTML parsing, DB insertion
│   ├── BooksAppRoomDatabase.kt
│   ├── daos/                   # Room DAOs
│   ├── download/FileDownload.kt
│   ├── entities/               # Room entities
│   └── repositories/           # Repository layer
└── ui/
    ├── BookReadingApp.kt       # Root composable + adaptive navigation
    ├── objects/                # Navigation, routes, top/bottom bars
    ├── screens/                # Home, Library, Bookshelf, ContentTable, Reading, Search
    ├── theme/                  # Material theme, color, shape, typography
    ├── utils/                  # Shared UI helpers
    └── viewmodels/             # ViewModels
```

## Prerequisites

- **Android Studio** (latest stable version recommended) with the Android SDK.
- Android SDK Platform **35** and a device/emulator running **Android 11 (API 30)** or higher.

## Installation

BookWorm is distributed as an Android APK. To install it on your phone:

1. **Build the APK:**

   ```
   ./gradlew assembleDebug
   ```

   The APK is generated at `app/build/outputs/apk/debug/app-debug.apk`.

2. **Transfer the APK** to your phone (e.g. via USB or cloud storage).

3. **Install it** — open the APK on your phone and allow installation from unknown sources when prompted.

4. **Open BookWorm.** Your phone must run **Android 11 (API 30)** or higher.

## Running Tests

- **Unit tests:**

  ```
  ./gradlew test
  ```

- **Instrumented tests** (requires a connected device or running emulator):

  ```
  ./gradlew connectedAndroidTest
  ```