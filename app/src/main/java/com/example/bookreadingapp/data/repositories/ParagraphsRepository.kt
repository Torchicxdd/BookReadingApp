package com.example.bookreadingapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bookreadingapp.data.daos.ParagraphDao
import com.example.bookreadingapp.data.entities.Paragraphs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ParagraphsRepository(private val paragraphDao: ParagraphDao) {
    val searchResults = MutableLiveData<List<Paragraphs>>()
    val allParagraphs: LiveData<List<Paragraphs>> = paragraphDao.getAllParagraphs()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun insertParagraph(newParagraph: Paragraphs) {
        coroutineScope.launch(Dispatchers.IO) {
            paragraphDao.insertParagraph(newParagraph)
        }
    }

    fun deleteParagraph(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            paragraphDao.deleteParagraph(id)
        }
    }

    fun findParagraphById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindParagraphById(id).await()
        }
    }
    fun findParagraphByBookId(id: Long){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindParagraphByBookId(id).await()
        }
    }

    private fun asyncFindParagraphById(id: Int) : Deferred<List<Paragraphs>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async paragraphDao.findParagraphById(id)
        }

    private fun asyncFindParagraphByBookId(id: Long) : Deferred<List<Paragraphs>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async paragraphDao.findParagraphByBookId(id)
        }

    fun findParagraphByChapterId(chapterId: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindParagraphByChapterId(chapterId).await()
        }
    }

    fun findParagraphsInAscOrder(chapterId: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindParagraphsInAscOrder(chapterId).await()
        }
    }

    private fun asyncFindParagraphsInAscOrder(chapterId: Long) : Deferred<List<Paragraphs>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async paragraphDao.findParagraphsInAscOrder(chapterId)
        }

    private fun asyncFindParagraphByChapterId(chapterId: Int) : Deferred<List<Paragraphs>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async paragraphDao.findParagraphByChapterId(chapterId)
        }
}