package com.example.bookreadingapp.objects

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Library : Routes("library")
    data object Search : Routes("search")
    data object ContentTable : Routes("content_table")
    data object Reading : Routes("reading")
    data object Bookshelf : Routes("bookshelf")
}