package com.example.bookreadingapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "chapters",
    foreignKeys = [
        ForeignKey(
            entity = Books::class,
            parentColumns = arrayOf("bookId"),
            childColumns = arrayOf("bookId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
class Chapters {
    @PrimaryKey(autoGenerate=true)

    @ColumnInfo(name="chapterId")
    var id: Long = 0

    @ColumnInfo(name="title")
    var title: String = ""

    @ColumnInfo(name="chapterPosition")
    var position: Int = 0

    var bookId: Long = 0

    constructor()
    constructor(title: String, position: Int, bookId: Long) {
        this.title = title
        this.position = position
        this.bookId = bookId
    }
}