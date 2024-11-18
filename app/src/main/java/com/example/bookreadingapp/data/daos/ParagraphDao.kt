package com.example.bookreadingapp.data.daos

import com.example.bookreadingapp.data.entities.Paragraphs
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ParagraphDao {
    @Insert
    fun insertParagraph(paragraph: Paragraphs)

    @Query("SELECT * FROM paragraphs WHERE paragraphId = :id")
    fun findParagraphById(id: Int): List<Paragraphs>

    @Query("SELECT * FROM paragraphs WHERE chapterId = :chapterId")
    fun findParagraphByChapterId(chapterId: Int): List<Paragraphs>

    @Query("DELETE FROM paragraphs WHERE paragraphId = :id")
    fun deleteParagraph(id: Int)
}