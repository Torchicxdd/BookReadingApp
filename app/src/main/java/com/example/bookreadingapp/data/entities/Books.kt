package com.example.bookreadingapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="books")
class Books {
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="bookId")
    var id: Int = 0
    @ColumnInfo(name="title")
    var title: String = ""
    @ColumnInfo(name="author")
    var author: String = ""
    @ColumnInfo(name="coverImage")
    var coverImage: String = ""

    constructor()
    constructor(title: String, author: String, coverImage: String) {
        this.title = title
        this.author = author
        this.coverImage = coverImage
    }
}