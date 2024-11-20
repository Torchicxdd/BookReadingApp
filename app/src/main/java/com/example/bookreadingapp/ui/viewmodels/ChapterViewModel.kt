package com.example.bookreadingapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookreadingapp.data.BooksAppRoomDatabase
import com.example.bookreadingapp.data.entities.Chapters
import com.example.bookreadingapp.data.repositories.ChaptersRepository

class ChapterViewModel(application: Application) : ViewModel() {
    val allChapters: LiveData<List<Chapters>>
    val searchResults: MutableLiveData<List<Chapters>>

    // Repository instance to interact with the database
    private val repository: ChaptersRepository

    init {
        // Initialize the database instance and the repository
        val db = BooksAppRoomDatabase.getInstance(application)
        val chaptersDao = db.chaptersDao()
        repository = ChaptersRepository(chaptersDao)
        allChapters = repository.allChapters
        searchResults = repository.searchResults
    }

    // Insert a new chapter
    fun insertChapter(chapter: Chapters) {
        repository.insertChapter(chapter)
    }

    // Insert multiple chapters
    fun insertManyChapters(chapters: List<Chapters>) {
        repository.insertManyChapters(chapters)
    }

    // Delete a chapter by its id
    fun deleteChapter(id: Int) {
        repository.deleteChapter(id)
    }

    // Delete all chapters by a specific bookId
    fun deleteChaptersByBookId(bookId: Int) {
        repository.deleteChaptersByBookId(bookId)
    }

    // Find a chapter by its id
    fun findChapterById(id: Int) {
        repository.findChapterById(id)
    }

    // Find chapters by their title
    fun findChapterByName(title: String) {
        repository.findChapterByName(title)
    }

    // Get chapters by bookId in ascending order of position
    fun getChaptersByBookId(bookId: Int) {
        repository.getChaptersByBookId(bookId)
    }

    // Update the title of a chapter
    fun updateChapterTitle(chapterId: Int, newTitle: String) {
        repository.updateChapterTitle(chapterId, newTitle)
    }

    // Update the position of a chapter
    fun updateChapterPosition(chapterId: Int, newPosition: Int) {
        repository.updateChapterPosition(chapterId, newPosition)
    }
}