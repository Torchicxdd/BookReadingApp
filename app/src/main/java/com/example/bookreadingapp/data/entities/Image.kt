package com.example.bookreadingapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = Chapters::class,
            parentColumns = arrayOf("chapterId"),
            childColumns = arrayOf("chapterId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
class Image {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "imageId")
    var id: Long = 0

    var chapterId: Long = 0

    @ColumnInfo(name = "imagePosition")
    var position: Int = 0

    var uri: String = ""

    constructor()

    constructor(uri: String, position: Int) {
        this.uri = uri
        this.position = position
    }
}