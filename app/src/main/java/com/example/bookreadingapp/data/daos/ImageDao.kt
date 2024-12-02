package com.example.bookreadingapp.data.daos

import android.icu.text.Transliterator.Position
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreadingapp.data.entities.Image

@Dao
interface ImageDao {

    @Insert
    suspend fun insertImage(image: Image)

    @Query("DELETE FROM images WHERE imageId = :id")
    fun deleteImage(id: Int)

    @Query("SELECT * FROM images WHERE imageId = :id")
    fun findImageById(id: Int): List<Image>

    @Query("SELECT * FROM images WHERE chapterId = :chapterId ORDER BY imagePosition ASC")
    fun findImagesInAscOrder(chapterId: Int): List<Image>
}