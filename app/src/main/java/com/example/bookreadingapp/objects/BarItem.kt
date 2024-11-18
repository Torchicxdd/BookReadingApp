import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.example.bookreadingapp.R

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String,
    val modifier: Modifier
)

object NavBarItems {
    fun getBarItems(context: Context): List<BarItem> {
        val items = mutableListOf<BarItem>()

        items.add(
            BarItem(
                title = context.getString(R.string.home_icon_title),
                image = Icons.Filled.Home,
                route = "home",
                modifier = Modifier.testTag("home_button")
            )
        )
        items.add(
            BarItem(
                title = context.getString(R.string.library_icon_title),
                image = Icons.Filled.MoreVert,
                route = "library",
                modifier = Modifier.testTag("library_button")
            )
        )
        items.add(
            BarItem(
                title = context.getString(R.string.bookshelf_icon_title),
                image = Icons.Filled.Menu,
                route = "bookshelf",
                modifier = Modifier.testTag("bookshelf_button")
            )
        )
        return items
    }
}