package com.example.bookreadingapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "paragraphs",
    foreignKeys = [
        ForeignKey(
            entity = Chapters::class,
            parentColumns = arrayOf("chapterId"),
            childColumns = arrayOf("chapterId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class Paragraphs {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "paragraphId")
    var id: Int = 0
    var chapterId: Int = 0
    var text: String = ""

    constructor()
    constructor(text: String, chapterId: Int) {
        this.text = text
        this.chapterId = chapterId
    }
}