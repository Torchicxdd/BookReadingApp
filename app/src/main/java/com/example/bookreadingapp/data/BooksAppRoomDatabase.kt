package com.example.bookreadingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookreadingapp.data.daos.BooksDao
import com.example.bookreadingapp.data.daos.ImageDao
import com.example.bookreadingapp.data.entities.Books
import com.example.bookreadingapp.data.entities.Image

@Database(entities = [Books::class, Image::class], version = 1)
abstract class BooksAppRoomDatabase: RoomDatabase() {
    abstract fun booksDao(): BooksDao
    abstract fun imageDao(): ImageDao

    companion object {
        private var INSTANCE: BooksAppRoomDatabase? = null
        fun getInstance(context: Context): BooksAppRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BooksAppRoomDatabase::class.java,
                        "books_app_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}