package com.example.bookreadingapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookreadingapp.data.daos.ChaptersDao
import com.example.bookreadingapp.data.entities.Chapters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChaptersRepository(private val chaptersDao: ChaptersDao) {
    val searchResults = MutableLiveData<List<Chapters>>()
    val allChapters: LiveData<List<Chapters>> = chaptersDao.getAllChapters()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Insert a new chapter into the database
     */
    suspend fun insertChapter(chapter: Chapters): Long {
        return withContext(Dispatchers.IO) {
            chaptersDao.insertChapter(chapter)
        }
    }

    /**
     * Insert multiple chapters into the database
     */
    suspend fun insertManyChapters(chapters: List<Chapters>) {
        coroutineScope.launch(Dispatchers.IO) {
            chaptersDao.insertManyChapters(chapters)
        }
    }

    /**
     * Delete a chapter by its id
     */
    fun deleteChapter(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            chaptersDao.deleteChapter(id)
        }
    }

    /**
     * Delete all chapters by bookId
     */
    fun deleteChaptersByBookId(bookId: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            chaptersDao.deleteChaptersByBookId(bookId)
        }
    }

    /**
     * Find a chapter by its id
     */
    fun findChapterById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = chaptersDao.findChapterById(id).value
        }
    }

    /**
     * Find chapters by their title
     */
    fun findChapterByName(title: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindChapterByName(title).value
        }
    }

    /**
     * Async function to find chapters by title
     */
    private fun asyncFindChapterByName(title: String): LiveData<List<Chapters>> {
        return chaptersDao.findChapterByName(title)
    }

    /**
     * Get chapters by bookId in ascending order of position
     */
    fun getChaptersByBookId(bookId: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncGetChaptersByBookId(bookId).value
        }
    }

    /**
     * Async function to get chapters by bookId in ascending order
     */
    private fun asyncGetChaptersByBookId(bookId: Long): LiveData<List<Chapters>> {
        return chaptersDao.getChaptersByBookId(bookId)
    }

    /**
     * Update the title of a chapter by its id
     */
    fun updateChapterTitle(chapterId: Int, newTitle: String) {
        coroutineScope.launch(Dispatchers.IO) {
            chaptersDao.updateChapterTitle(chapterId, newTitle)
        }
    }

    /**
     * Update the position of a chapter by its id
     */
    fun updateChapterPosition(chapterId: Int, newPosition: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            chaptersDao.updateChapterPosition(chapterId, newPosition)
        }
    }
}
