package com.example.bookreadingapp.data.entities

@Entity(
    tableName = "images",
    foreignKeys = [(
            entity = Chapters::class,
            parentColumns = arrayOf("chaperId"),
            childColumns = arrayOf("chapterId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
            )]
)
class Images {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "imageId")
    var id: Int = 0

    var chapterId: Int = 0

    var uri: String = ""

    constructor()

    constructor(uri: String) {
        this.uri = uri
    }
}