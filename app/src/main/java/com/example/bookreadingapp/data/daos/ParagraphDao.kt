package com.example.bookreadingapp.data.daos

import androidx.lifecycle.LiveData
import com.example.bookreadingapp.data.entities.Paragraphs
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreadingapp.data.entities.Chapters

@Dao
interface ParagraphDao {
    @Insert
    suspend fun insertParagraph(paragraph: Paragraphs)

    @Query("SELECT * FROM paragraphs WHERE paragraphId = :id")
    fun findParagraphById(id: Int): List<Paragraphs>

    @Query("SELECT * FROM paragraphs WHERE chapterId = :chapterId")
    fun findParagraphByChapterId(chapterId: Int): List<Paragraphs>

    @Query("DELETE FROM paragraphs WHERE paragraphId = :id")
    fun deleteParagraph(id: Int)

    @Query("SELECT * FROM paragraphs WHERE chapterId = :chapterId ORDER BY paragraphPosition ASC")
    fun findParagraphsInAscOrder(chapterId: Long): List<Paragraphs>

    // Get all chapters as LiveData
    @Query("SELECT * FROM paragraphs")
    fun getAllParagraphs(): LiveData<List<Paragraphs>>

    @Query("SELECT * FROM paragraphs INNER JOIN chapters USING(chapterId) WHERE bookId = :bookId")
    fun findParagraphByBookId(bookId: Long): List<Paragraphs>
}