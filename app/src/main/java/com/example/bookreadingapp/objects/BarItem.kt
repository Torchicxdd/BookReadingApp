import android.content.Context
import androidx.compose.material.icons.Icons
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
    fun getBarItems(context: Context): List<BarItem> {
        val items = mutableListOf<BarItem>()

        items.add(
            BarItem(
                title = context.getString(R.string.home_icon_title),
                image = Icons.Filled.Home,
                route = "home"
            )
        )
        items.add(
            BarItem(
                title = context.getString(R.string.library_icon_title),
                image = Icons.Filled.MoreVert,
                route = "library"
            )
        )
        items.add(
            BarItem(
                title = context.getString(R.string.search_icon_title),
                image = Icons.Filled.Search,
                route = "search"
            )
        )
        items.add(
            BarItem(
                title = context.getString(R.string.table_content_icon_title),
                image = Icons.Filled.Menu,
                route = "content_table"
            )
        )
        items.add(
            BarItem(
                title = context.getString(R.string.reading_mode_icon_title),
                image = Icons.Filled.PlayArrow,
                route = "reading"
            )
        )

        return items
    }
}