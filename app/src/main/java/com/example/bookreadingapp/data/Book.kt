package com.example.bookreadingapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.entities.Books
import com.example.bookreadingapp.data.entities.Chapters
import com.example.bookreadingapp.ui.viewmodels.BookViewModel
import com.example.bookreadingapp.ui.viewmodels.ChapterViewModel
import com.example.bookreadingapp.ui.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class Book (
    @DrawableRes val imageResourceId: Int,
    @StringRes val title: Int,
    val arrayIndex: Int
) {
    var htmlFilePath: String = ""
    var inserted = false;

    private fun readHtmlFile(): String {
        val htmlFile = File(this.htmlFilePath)

        return htmlFile.readText()
    }

    private fun parseHtml(html: String): List<String> {
        val content = Jsoup.parse(html)
        val elements = mutableListOf<String>()

        content.body().children().forEach { element ->
            parseElement(element, elements)
        }

        return elements
    }

    private fun parseElement(element: Element, elements: MutableList<String>) {
        when (element.tagName()) {
            // Mark where images are
            "img" -> elements.add("<img>-PLACEHOLDER")
            // Mark where paragraphs begin and end
            "p" -> {
                elements.add("<p>-Start" + element.text())
            }
            // Mark where chapters begin and end
            "h2" -> {
                elements.add("<h2>-Start" + element.text())
            }
            // Mark where tables begin and end
            "table" -> parseTableStringBuild(element, elements)
            else -> {
                // Avoid adding empty strings
                if (element.text() != "") {
                    elements.add(element.text())
                }
                element.children().forEach { child ->
                    parseElement(child, elements)
                }
            }
        }
    }

    // Parsing used if we're simply displaying text for the table
    private fun parseTableStringBuild(element: Element, elements: MutableList<String>) {
        val table = StringBuilder()
        table.append("\n")
        element.select("tr").forEach { row ->
            val tableRow = StringBuilder()
            for (data in row.children()) {
                if (tableRow.length > 0) {
                    tableRow.append("|")
                }
                tableRow.append(data.text())
            }
            tableRow.append("|")
            table.append(tableRow).append("\n")
        }
        table.append("\n")
        elements.add(table.toString())
    }

    // Parsing used if we're using WebView display for tables
    private fun parseTableWebView(element: Element, elements: MutableList<String>) {
        val table = StringBuilder()
        table.append("<table>")
        element.select("tr").forEach { row ->
            table.append("<tr>")
            row.select("td, th").forEach { data ->
                table.append("<").append(data.tagName()).append(">")
                table.append(data.text())
                table.append("</").append(data.tagName()).append(">")
            }
            table.append("</tr>")
        }
        table.append("</table>")
        elements.add(table.toString())
    }

    fun exampleHtmlParsing() {
        val elements = parseHtml(readHtmlFile())
        var insideTable = true

        // Table elements located between "<table>-Start" & "<table>-End"
        for (element in elements) {
            if(element.contains("<table>-Start")) {
                insideTable = true
            }
            if (element.contains("<table>-End")) {
                Log.i("HtmlParser", element)
                insideTable = false
            }
            if(insideTable) {
                Log.i("HtmlParser", element)
            }
        }
    }

    fun insertBook(mainViewModel: MainViewModel) {
        mainViewModel.bookViewModel.insertBook(
            Books(
                title.toString(), "AUTHOR_HERE", imageResourceId.toString()
            )
        )
    }

    fun insertChapters(mainViewModel: MainViewModel) {
        val elements = parseHtml(readHtmlFile())
        var chapterPosition = 1;

        // Insert chapters into database
        mainViewModel.viewModelScope.launch {
            for (e in elements) {
                if (e.contains("<h2>-Start")) {
                    val chapter = Chapters(
                        title = e.replace("<h2>-Start", ""),
                        position = chapterPosition,
                        bookId = 0
                    )
                    mainViewModel.chapterViewModel.insertChapter(chapter)
                    chapterPosition++
                }
            }
        }
    }
}

val books = listOf(
    Book(R.drawable.wood_cover, R.string.wood, 0),
    Book(R.drawable.plumbing_cover, R.string.plumbing, 1),
    Book(R.drawable.hardware_cover, R.string.hardware, 2),
    Book(R.drawable.steam_cover, R.string.steam, 3),
    Book(R.drawable.dairy_cover, R.string.dairy, 4),
    Book(R.drawable.mushroom_cover, R.string.mushroom, 5)
)