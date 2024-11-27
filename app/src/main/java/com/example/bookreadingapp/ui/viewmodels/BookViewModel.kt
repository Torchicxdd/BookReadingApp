package com.example.bookreadingapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookreadingapp.data.BooksAppRoomDatabase
import com.example.bookreadingapp.data.entities.Books
import com.example.bookreadingapp.data.repositories.BooksRepository

class BookViewModel(application: Application) : ViewModel() {
    val allBooks: LiveData<List<Books>>
    private val repository: BooksRepository
    val searchResults: MutableLiveData<List<Books>>

    init {
        val bookDb = BooksAppRoomDatabase.getInstance(application)
        val booksDao = bookDb.booksDao()
        repository = BooksRepository(booksDao)
        allBooks = repository.allBooks
        searchResults = repository.searchResults
    }

    fun insertBook(book: Books) {
        repository.insertBook(book)
    }

    fun deleteBook(id: Int) {
        repository.deleteBook(id)
    }

    fun findBookById(id: Int) {
        repository.findBookById(id)
    }

    fun findBookByName(title: String) {
        repository.findBookByName(title)
    }

    fun findBookByNameAndAuthor(title: String, author: String) {
        repository.findBookByNameAndAuthor(title, author)
    }
}