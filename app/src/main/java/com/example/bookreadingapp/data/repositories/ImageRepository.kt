package com.example.bookreadingapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.bookreadingapp.data.daos.ImageDao
import com.example.bookreadingapp.data.entities.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ImageRepository(private val imageDao: ImageDao) {
    val searchResults = MutableLiveData<List<Image>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun insertImage(newImage: Image) {
        coroutineScope.launch(Dispatchers.IO) {
            imageDao.insertImage(newImage)
        }
    }

    fun deleteImage(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            imageDao.deleteImage(id)
        }
    }

    fun findImageById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindImageById(id).await()
        }
    }

    fun findImagesInAscOrder(chapterId: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindImagesInAscOrder(chapterId).await()
        }
    }

    private fun asyncFindImageById(id: Int) : Deferred<List<Image>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async imageDao.findImageById(id)
        }

    private fun asyncFindImagesInAscOrder(chapterId: Long) : Deferred<List<Image>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async imageDao.findImagesInAscOrder(chapterId)
        }
}