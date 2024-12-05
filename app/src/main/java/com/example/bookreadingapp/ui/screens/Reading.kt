package com.example.bookreadingapp.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.entities.Image
import com.example.bookreadingapp.data.entities.Paragraphs
import com.example.bookreadingapp.data.entities.Table
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import java.io.File


/**
 * Reading screen containing the book to read
 */
@Composable
fun Reading(
    book: Book?,
    readingMode: Boolean,
    toggleReadingMode: () -> Unit,
    currentChapterId: Long?,
    changeChapter: (Long) -> Unit,
    viewModel: AppViewModel,
    mainViewModel: MainViewModel,
    createPages: (List<String>, TextMeasurer, Float, Dp, TextUnit, Density) -> Unit,
    createStringList: (List<Paragraphs>, List<Table>, List<Image>) -> List<String>,
    pages: List<List<String>>,
    pageLazyListState: LazyListState,
    resetScroll: suspend () -> Unit
) {
    // Query paragraphs, tables and images in current chapter
    if (currentChapterId != null) {
        mainViewModel.paragraphViewModel.findParagraphsInAscOrder(currentChapterId)
        mainViewModel.tableViewModel.findTablesInAscOrder(currentChapterId)
        mainViewModel.imageViewModel.findImagesInAscOrder(currentChapterId)
    }

    val verticalScrollState = rememberScrollState()
    val searchParagraphsResult by mainViewModel.paragraphViewModel.searchedResults.observeAsState(listOf())
    val searchTablesResult by mainViewModel.tableViewModel.searchedResults.observeAsState(listOf())
    val searchImagesResult by mainViewModel.imageViewModel.searchedResults.observeAsState(listOf())

    val stringList = createStringList(searchParagraphsResult, searchTablesResult, searchImagesResult)
    // Find parent directory
    var parentDirectory = ""
    if (book != null) {
        parentDirectory = File(book.htmlFilePath).parent?: ""
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // tap gesture for toggling reading mode
                detectTapGestures(
                    onTap = { toggleReadingMode() }
                )
            }
            .testTag("reading_screen")
    ) {
        val boxWithConstraintsScope = this
        ChapterDisplay(
            parentDirectory = parentDirectory,
            paragraphs = stringList,
            width = boxWithConstraintsScope.maxWidth,
            height = boxWithConstraintsScope.maxHeight,
            createPages = createPages,
            pages = pages,
            verticalScrollState = verticalScrollState,
            pageLazyListState = pageLazyListState
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        // Display the chapter navigation only if not in reading mode
        if (!readingMode) {
            ChapterNavigation(
                currentChapterId = currentChapterId,
                currentChapterList = viewModel.currentBookChapterList,
                changeChapter = changeChapter,
                resetScroll = resetScroll,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun ChapterDisplay(
    parentDirectory: String?,
    paragraphs: List<String>,
    height: Dp,
    width: Dp,
    createPages: (
        List<String>, TextMeasurer, Float, Dp, TextUnit, Density
    ) -> Unit,
    verticalScrollState: ScrollState,
    pages: List<List<String>>,
    pageLazyListState: LazyListState,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val maxHeightPx = with(density) { height.toPx() }
    val textSizeInSp = with(density) {
        dimensionResource(R.dimen.text_size).toSp()
    }

    createPages(paragraphs, textMeasurer, maxHeightPx, width, textSizeInSp, density)

    LazyRow(state = pageLazyListState) {
        items(pages) { page ->
            PageDisplay(
                parentDirectory = parentDirectory,
                paragraphs = page,
                width = width,
                height = height,
                textSize = textSizeInSp,
                verticalScrollState = verticalScrollState
            )
        }
    }
}

@Composable
fun PageDisplay(
    parentDirectory: String?,
    paragraphs: List<String>,
    height: Dp,
    width: Dp,
    textSize: TextUnit,
    verticalScrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .width(width)
            .height(height)
            .verticalScroll(verticalScrollState)
    ) {
        for (paragraph in paragraphs) {
            // Load image from local storage if contains <img> tag
            if (paragraph.contains("<img>")) {
                StorageImage(
                    parentDirectory = parentDirectory,
                    imageName = paragraph.replace("<img>", "")
                )
            } else {
                Text(
                    text = paragraph,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = textSize
                )
            }
        }
    }
}

/**
 * Load and display an image from app's internal storage
 */
@Composable
fun StorageImage(
    parentDirectory: String?,
    imageName: String
) {
    val imagePath = parentDirectory + imageName
    val imageFile = File(imagePath)

    if (imageFile.exists()) {
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = imageName,
            modifier = Modifier.size(100.dp)
        )
    }
}

/**
 * The button used to navigate through a chapter
 */
@Composable
fun ChapterNavigation(
    currentChapterId: Long?,
    currentChapterList: List<Long>,
    changeChapter: (Long) -> Unit,
    resetScroll: suspend () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ){
        val coroutineScope = rememberCoroutineScope()

        Button(
            onClick = {
                coroutineScope.launch {
                    resetScroll()
                    if (currentChapterId != null) {
                        val previousChapter = currentChapterId - 1
                        if (currentChapterList.contains(previousChapter)) {
                            changeChapter(previousChapter)
                        }
                    }
                }
            },
        ){
            Text(
                text = stringResource(R.string.prev_chap),
                style = MaterialTheme.typography.labelSmall
            )
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    resetScroll()
                    if (currentChapterId != null) {
                        val nextChapter = currentChapterId + 1
                        if (currentChapterList.contains(nextChapter)) {
                            changeChapter(nextChapter)
                        }
                    }
                }
            },
        ){
            Text(
                text = stringResource(R.string.next_chap),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

