package com.example.bookreadingapp.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreadingapp.data.entities.Books

@Dao
interface BooksDao {
    @Insert
    suspend fun insertBook(book: Books): Long

    @Query("SELECT * FROM books")
    fun getAllBooks(): LiveData<List<Books>>

    @Query("SELECT * FROM books WHERE bookId=:id")
    fun findBookById(id: Int): List<Books>

    @Query("SELECT * FROM books WHERE title=:title")
    fun findBookByName(title: String): List<Books>

    @Query("SELECT * FROM books WHERE title=:title AND author=:author")
    fun findBookByNameAndAuthor(title: String, author: String): List<Books>

    @Query("DELETE FROM books WHERE bookId=:id")
    fun deleteBook(id: Int)
}