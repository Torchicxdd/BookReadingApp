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
    var id: Int = 0

    @ColumnInfo(name="title")
    var title: String = ""

    var bookId: Int = 0

    constructor()
    constructor(title: String) {
        this.title = title
    }
}