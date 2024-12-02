package com.example.bookreadingapp.data

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.entities.Books
import com.example.bookreadingapp.data.entities.Chapters
import com.example.bookreadingapp.data.entities.Image
import com.example.bookreadingapp.data.entities.Paragraphs
import com.example.bookreadingapp.data.entities.Table
import com.example.bookreadingapp.ui.viewmodels.MainViewModel
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File

class Book (
    @DrawableRes val imageResourceId: Int,
    @StringRes val title: Int,
    val arrayIndex: Int
) {
    var htmlFilePath: String = ""

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
            "img" -> elements.add("<img>-PLACEHOLDER src=\"" + element.attr("src") + "\"")
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
        table.append("<table>-Start")
        table.append("\n")
        element.select("tr").forEach { row ->
            val tableRow = StringBuilder()
            for (data in row.children()) {
                if (tableRow.isNotEmpty()) {
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

    suspend fun insertBook(mainViewModel: MainViewModel): Long {
        val newBookID = mainViewModel.bookViewModel.insertBook(
            Books(
                title.toString(), "AUTHOR_HERE", imageResourceId.toString()
            )
        )
        return newBookID
    }

    suspend fun insertElements(newBookID: Long, mainViewModel: MainViewModel) {
        val elements = parseHtml(readHtmlFile())
        var currentChapterID: Long = 0
        var chapterPosition = 1
        var elementPosition = 0

        for (e in elements) {
            Log.d("long message", e)
            // Insert chapters
            if (e.contains("<h2>-Start")) {
                // Reset element positions to zero at the start of every chapter
                elementPosition = 0
                currentChapterID = mainViewModel.chapterViewModel.insertChapter(
                    Chapters(e.replace("<h2>-Start", ""), chapterPosition, newBookID)
                )
                chapterPosition ++
            }
            // Insert tables
            if (e.contains("<table>-Start")) {
                mainViewModel.tableViewModel.insertTable(
                    Table(e.replace("<table>-Start", ""), currentChapterID, elementPosition)
                )
                elementPosition ++
            }
            // Insert paragraphs
            if (e.contains("<p>-Start")) {
                mainViewModel.paragraphViewModel.insertParagraph(
                    Paragraphs(e.replace("<p>-Start", ""), currentChapterID, elementPosition)
                )
                elementPosition ++
            }
            // This thing is not working
            // Insert images
            if (e.contains("<img>-PLACEHOLDER src=\"")) {
                val imgSrc = e.substringAfter("src=\"").substringBefore("\"")
                val validImgSrc = imgSrc.trim()
                if (validImgSrc.isNotEmpty()) {
                    val imgPathWithPrefix = "./$validImgSrc"
                    val img = Image(imgPathWithPrefix, elementPosition)
                    Log.e("Image Parsing", "Image URI: ${img.uri}")
                    mainViewModel.imageViewModel.insertImage(img)
                    elementPosition++
                } else {
                    Log.e("Image Parsing", "Image src is empty or invalid at position $elementPosition")
                }
            } else {
                Log.e("Image Parsing", "Didn't even start first if")
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