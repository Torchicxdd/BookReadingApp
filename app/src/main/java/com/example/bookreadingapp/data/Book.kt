package com.example.bookreadingapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import android.util.Log
import com.example.bookreadingapp.R
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
}

val books = listOf(
    Book(R.drawable.wood_cover, R.string.wood, 0),
    Book(R.drawable.plumbing_cover, R.string.plumbing, 1),
    Book(R.drawable.hardware_cover, R.string.hardware, 2),
    Book(R.drawable.steam_cover, R.string.steam, 3),
    Book(R.drawable.dairy_cover, R.string.dairy, 4),
    Book(R.drawable.mushroom_cover, R.string.mushroom, 5)
)