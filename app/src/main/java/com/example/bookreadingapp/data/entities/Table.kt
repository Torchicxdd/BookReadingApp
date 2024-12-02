package com.example.bookreadingapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tables",
    foreignKeys = [
        ForeignKey(
            entity = Chapters::class,
            parentColumns = arrayOf("chapterId"),
            childColumns = arrayOf("chapterId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
class Table {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tableId")
    var id: Long = 0

    var chapterId: Long = 0

    @ColumnInfo(name = "tablePosition")
    var position: Int = 0

    var content: String = ""

    constructor()
    constructor(content: String, chapterId: Long, position: Int) {
        this.content = content
        this.chapterId = chapterId
        this.position = position
    }
}