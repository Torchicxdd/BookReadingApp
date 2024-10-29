package com.example.bookreadingapp.objects

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)

object NavBarItems {
    val  BarItems = listOf(
        BarItem(
            title = "Home",
            image = Icons.Filled.Home,
            route = "home"
        ),
        BarItem(
            title = "Library",
            image = Icons.Filled.MoreVert,
            route = "library"
        ),
        BarItem(
            title = "Search",
            image = Icons.Filled.Search,
            route = "search"
        ),
        BarItem(
            title = "Table of Content",
            image = Icons.Filled.Menu,
            route = "content_table"
        ),
        BarItem(
            title = "Reading Mode",
            image = Icons.Filled.PlayArrow,
            route = "reading"
        )
    )
}