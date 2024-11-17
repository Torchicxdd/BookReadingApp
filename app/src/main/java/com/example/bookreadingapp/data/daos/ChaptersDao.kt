package com.example.bookreadingapp.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreadingapp.data.entities.Chapters

@Dao
interface ChaptersDao {

    // Insert a new chapter
    @Insert
    fun insertChapter(chapter: Chapters)

    // Insert multiple chapters at once
    @Insert
    fun insertManyChapters(chapters: List<Chapters>)

    // Get all chapters as LiveData
    @Query("SELECT * FROM chapters")
    fun getAllChapters(): LiveData<List<Chapters>>

    // Find a chapter by its id
    @Query("SELECT * FROM chapters WHERE chapterId = :id")
    fun findChapterById(id: Int): LiveData<List<Chapters>>

    // Find chapters by their title
    @Query("SELECT * FROM chapters WHERE title = :title")
    fun findChapterByName(title: String): LiveData<List<Chapters>>

    // Get all chapters by bookId in ascending order
    @Query("SELECT * FROM chapters WHERE bookId = :bookId ORDER BY chapterPosition ASC")
    fun getChaptersByBookId(bookId: Int): LiveData<List<Chapters>>

    // Delete a chapter by its id
    @Query("DELETE FROM chapters WHERE chapterId = :id")
    fun deleteChapter(id: Int)

    // Delete all chapters for a particular book
    @Query("DELETE FROM chapters WHERE bookId = :bookId")
    fun deleteChaptersByBookId(bookId: Int)

    // Update only the chapter title
    @Query("UPDATE chapters SET title = :newTitle WHERE chapterId = :chapterId")
    fun updateChapterTitle(chapterId: Int, newTitle: String)

    // Update chapter position
    @Query("UPDATE chapters SET chapterPosition = :newPosition WHERE chapterId = :chapterId")
    fun updateChapterPosition(chapterId: Int, newPosition: Int)

}