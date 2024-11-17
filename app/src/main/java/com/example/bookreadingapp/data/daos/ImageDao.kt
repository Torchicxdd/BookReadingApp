package com.example.bookreadingapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreadingapp.data.entities.Image

@Dao
interface ImageDao {

    @Insert
    fun insertImage(image: Image)

    @Query("DELETE FROM images WHERE imageId = :id")
    fun deleteImage(id: Int)
}