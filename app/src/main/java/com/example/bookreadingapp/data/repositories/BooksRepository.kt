package com.example.bookreadingapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.bookreadingapp.data.daos.BooksDao
import com.example.bookreadingapp.data.entities.Books
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksRepository(private val booksDao: BooksDao) {
    val searchResults = MutableLiveData<List<Books>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertBook(newBook: Books) {
        coroutineScope.launch(Dispatchers.IO) {
            booksDao.insertBook(newBook)
        }
    }

}