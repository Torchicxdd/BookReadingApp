package com.example.bookreadingapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookreadingapp.data.BooksAppRoomDatabase
import com.example.bookreadingapp.data.entities.Paragraphs
import com.example.bookreadingapp.data.repositories.ParagraphsRepository

class ParagraphViewModel(application: Application) : ViewModel() {
    val searchedResults: MutableLiveData<List<Paragraphs>>
    private val repository: ParagraphsRepository

    init {
        val paragraphDb = BooksAppRoomDatabase.getInstance(application)
        val paragraphDao = paragraphDb.paragraphDao()
        repository = ParagraphsRepository(paragraphDao)

        searchedResults = repository.searchResults
    }

    suspend fun insertParagraph(paragraph: Paragraphs) {
        repository.insertParagraph(paragraph)
    }

    fun deleteParagraph(id: Int) {
        repository.deleteParagraph(id)
    }

    fun findParagraphById(id: Int) {
        repository.findParagraphById(id)
    }

    fun findParagraphByChapterId(chapterId: Int) {
        repository.findParagraphByChapterId(chapterId)
    }

    fun findParagraphsInAscOrder(chapterId: Long) {
        repository.findParagraphsInAscOrder(chapterId)
    }
}