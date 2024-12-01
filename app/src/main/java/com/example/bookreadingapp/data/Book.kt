package com.example.bookreadingapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.jsoup.Jsoup;
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
            "img" -> elements.add("IMAGE PLACEHOLDER")
            "p" -> {
                elements.add("<p>-Start" + element.text())
            }
            else -> {
                elements.add(element.text())
                element.children().forEach { child ->
                    parseElement(child, elements)
                }
            }
        }
    }

    fun exampleHtmlParsing() {
        val html = readHtmlFile()
        val elements = parseHtml(html)

        for (i in 0..500) {
            if (elements[i].contains("<p>-Start")) {
                Log.i("HtmlParser", elements[i].replace("<p>-Start", ""))
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