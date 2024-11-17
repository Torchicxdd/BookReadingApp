package com.example.bookreadingapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.bookreadingapp.data.daos.ImageDao
import com.example.bookreadingapp.data.entities.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagesRepository(private val imageDao: ImageDao) {

    val searchResults = MutableLiveData<List<Image>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertImage(newImage: Image) {
        coroutineScope.launch(Dispatchers.IO) {
            imageDao.insertImage(newImage)
        }
    }

    fun deleteImage(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            imageDao.deleteImage(id)
        }
    }
}