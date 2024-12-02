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
    var id: Long = 0

    var chapterId: Long = 0

    @ColumnInfo(name="paragraphPosition")
    var position: Int = 0

    var text: String = ""

    constructor()

    constructor(text: String, chapterId: Long, position: Int) {
        this.text = text
        this.chapterId = chapterId
        this.position = position
    }
}