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
import com.example.bookreadingapp.R

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)

object NavBarItems {
    val  BarItems = listOf(
        BarItem(
            title = R.string.home_icon_title.toString(),
            image = Icons.Filled.Home,
            route = "home"
        ),
        BarItem(
            title = R.string.library_icon_title.toString(),
            image = Icons.Filled.MoreVert,
            route = "library"
        ),
        BarItem(
            title = R.string.search_icon_title.toString(),
            image = Icons.Filled.Search,
            route = "search"
        ),
        BarItem(
            title = R.string.table_content_icon_title.toString(),
            image = Icons.Filled.Menu,
            route = "content_table"
        ),
        BarItem(
            title = R.string.reading_mode_icon_title.toString(),
            image = Icons.Filled.PlayArrow,
            route = "reading"
        )
    )
}