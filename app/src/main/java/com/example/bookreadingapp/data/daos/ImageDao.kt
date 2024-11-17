package com.example.bookreadingapp.data.daos

import com.example.bookreadingapp.data.entities.Image

@Dao
interface ImageDao {

    @Insert
    fun insertImage(image: Image)

    @Query("DELETE FROM images WHERE imageId = :id")
    fun deleteImage(id: Int)
}