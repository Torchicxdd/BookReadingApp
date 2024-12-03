package com.example.bookreadingapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookreadingapp.R
import com.example.bookreadingapp.data.Book
import com.example.bookreadingapp.data.entities.Image
import com.example.bookreadingapp.data.entities.Paragraphs
import com.example.bookreadingapp.data.entities.Table
import com.example.bookreadingapp.ui.utils.BookCover
import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import com.example.bookreadingapp.ui.viewmodels.MainViewModel


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
    createPages: (List<String>, TextMeasurer, Float, Dp, TextUnit, Density) -> List<List<String>>,
    createStringList: (List<Paragraphs>, List<Table>, List<Image>) -> List<String>
) {
    // Query paragraphs, tables and images in current chapter
    if (currentChapterId != null) {
        mainViewModel.paragraphViewModel.findParagraphsInAscOrder(currentChapterId)
        mainViewModel.tableViewModel.findTablesInAscOrder(currentChapterId)
        mainViewModel.imageViewModel.findImagesInAscOrder(currentChapterId)
    }

    val searchParagraphsResult by mainViewModel.paragraphViewModel.searchedResults.observeAsState(listOf())
    val searchTablesResult by mainViewModel.tableViewModel.searchedResults.observeAsState(listOf())
    val searchImagesResult by mainViewModel.imageViewModel.searchedResults.observeAsState(listOf())

    val stringList = createStringList(searchParagraphsResult, searchTablesResult, searchImagesResult)

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
            paragraphs = stringList,
            width = boxWithConstraintsScope.maxWidth,
            height = boxWithConstraintsScope.maxHeight,
            createPages = createPages
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
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun ChapterDisplay(
    paragraphs: List<String>,
    height: Dp,
    width: Dp,
    createPages: (
        List<String>, TextMeasurer, Float, Dp, TextUnit, Density
    ) -> List<List<String>>
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val maxHeightPx = with(density) { height.toPx() }
    val textSizeInSp = with(density) {
        dimensionResource(R.dimen.text_size).toSp()
    }

    val pages = createPages(
        paragraphs,
        textMeasurer,
        maxHeightPx,
        width,
        textSizeInSp,
        density
    )

    LazyRow {
        items(pages) { page ->
            PageDisplay(
                paragraphs = page,
                width = width,
                height = height,
                textSize = textSizeInSp
            )
        }
    }
}

@Composable
fun PageDisplay(
    paragraphs: List<String>,
    height: Dp,
    width: Dp,
    textSize: TextUnit
) {
    Column(
        modifier = Modifier
            .width(width)
            .height(height)
    ) {
        for (paragraph in paragraphs) {
            Text(
                text = paragraph,
                modifier = Modifier.fillMaxWidth(),
                fontSize = textSize
            )
        }
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
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ){
        Button(
            onClick = {
                if (currentChapterId != null) {
                    val previousChapter = currentChapterId - 1
                    if (currentChapterList.contains(previousChapter)) {
                        changeChapter(previousChapter)
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
                if (currentChapterId != null) {
                    val nextChapter = currentChapterId + 1
                    if (currentChapterList.contains(nextChapter)) {
                        changeChapter(nextChapter)
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

