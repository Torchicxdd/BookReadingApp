package com.example.bookreadingapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookreadingapp.data.daos.BooksDao
import com.example.bookreadingapp.data.entities.Books
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BooksRepository(private val booksDao: BooksDao) {
    val searchResults = MutableLiveData<List<Books>>()
    val allBooks: LiveData<List<Books>> = booksDao.getAllBooks()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Method used to insert new books to the database
     */
    fun insertBook(newBook: Books) {
        coroutineScope.launch(Dispatchers.IO) {
            booksDao.insertBook(newBook)
        }
    }

    /**
     * Method used to delete books from the database
     */
    fun deleteBook(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            booksDao.deleteBook(id)
        }
    }

    /**
     * Method used to find books in the database, based on a given id
     */
    fun findBookById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindBookById(id).await()
        }
    }

    /**
     * Async function for the method findBookById
     */
    private fun asyncFindBookById(id: Int): Deferred<List<Books>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async booksDao.findBookById(id)
        }

    /**
     * Method used to find books in the database, based on a given name/title
     */
    fun findBookByName(title: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindBookByName(title).await()
        }
    }

    /**
     * Async function for the method findBookByName
     */
    private fun asyncFindBookByName(title: String): Deferred<List<Books>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async booksDao.findBookByName(title)
        }

    /**
     * Method used to find books in the database, based on a given name/title and author name
     */
    fun findBookByNameAndAuthor(title: String, author: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindBookByNameAndAuthor(title, author).await()
        }
    }

    /**
     * Async function for the method findBookByNameAndAuthor
     */
    private fun asyncFindBookByNameAndAuthor(title: String, author: String): Deferred<List<Books>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async booksDao.findBookByNameAndAuthor(title, author)
        }
}