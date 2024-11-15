package com.example.bookreadingapp.data.entities

class Books {
    var id: Int = 0
    var title: String = ""
    var author: String = ""
    var coverImage: String = ""

    constructor()
    constructor(title: String, author: String, coverImage: String) {
        
    }
}