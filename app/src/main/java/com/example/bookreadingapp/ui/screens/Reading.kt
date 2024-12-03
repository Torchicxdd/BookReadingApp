package com.example.bookreadingapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
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
    mainViewModel: MainViewModel
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // tap gesture for toggling reading mode
                detectTapGestures(
                    onTap = { toggleReadingMode() }
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .testTag("reading_screen")
        ) {
            Text(
                text = stringResource(R.string.reading),
                style = MaterialTheme.typography.displayLarge
            )
            PageScrollLazyColumn(
                book = book,
                textList = stringList
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
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

private fun createStringList(
    paragraphList: List<Paragraphs>,
    tableList: List<Table>,
    imageList: List<Image>
): List<String> {
    var paragraphPosition = 0
    var tablePosition = 0
    var imagePosition = 0

    val elementSize = paragraphList.size + tableList.size + imageList.size
    var elementPosition = 0
    val stringList: MutableList<String> = mutableListOf()

    if (elementSize == 0) {
        stringList.add("No Text")
        return stringList
    }

    // Create a string list of all texts to be displayed
    while(elementPosition < elementSize) {
        if (paragraphList.isNotEmpty() &&
            paragraphPosition < paragraphList.size &&
            paragraphList[paragraphPosition].position == elementPosition) {
            stringList.add(paragraphList[paragraphPosition].text)
            paragraphPosition++
        }
        if (tableList.isNotEmpty() &&
            tablePosition < tableList.size &&
            tableList[tablePosition].position == elementPosition) {
            stringList.add(tableList[tablePosition].content)
            tablePosition++
        }
        if (imageList.isNotEmpty() &&
            imagePosition < imageList.size &&
            imageList[imagePosition].position == elementPosition) {
            stringList.add(imageList[imagePosition].uri)
            imagePosition++
        }
        elementPosition++
    }

    return stringList
}

/**
 * Book display on the reading screen
 */
@Composable
fun BookDisplay(
    @DrawableRes imageResourceId: Int,
    @StringRes titleResourceId: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        BookCover(imageResourceId)
        Text(
            text = stringResource(titleResourceId),
            style = MaterialTheme.typography.displayMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))
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
/**
 * Column where book text will be placed
 */
@Composable
fun PageScrollLazyColumn(
    book: Book?,
    textList: List<String>
) {
    var currentPage by rememberSaveable { mutableStateOf(0) }

    val swipeThreshold = 300f
    var swipeDetected by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }

    var itemsPerPage by remember { mutableStateOf(1) }

    val boxModifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned { coordinates ->
            val height = coordinates.size.height.toFloat()
            itemsPerPage = (height / 260).toInt()
        }

    val chunkedPages = textList.chunked(itemsPerPage)

    val swipeModifier = Modifier.pointerInput(Unit) {
        detectHorizontalDragGestures { change, dragAmount ->
            dragOffset += dragAmount * 0.5f

            if (dragOffset > swipeThreshold && !swipeDetected) {
                if (currentPage > 0) {
                    currentPage--
                    swipeDetected = true
                }
            } else if (dragOffset < -swipeThreshold && !swipeDetected) {
                if (currentPage < chunkedPages.lastIndex) {
                    currentPage++
                    swipeDetected = true
                }
            }
            if (swipeDetected) {
                dragOffset = 0f
            }
        }
    }

    LaunchedEffect(currentPage) {
        swipeDetected = false
    }

    Box(modifier = boxModifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                //.border(BorderStroke(4.dp, Color.Black), RectangleShape)
                .padding(16.dp)
                .align(Alignment.Center)
                .then(swipeModifier)
        ) {
            if (currentPage == 0) {
                item {
                    book?.let {
                        BookDisplay(
                            imageResourceId = it.imageResourceId,
                            titleResourceId = it.title
                        )
                    }
                }
            }
            else{
                items(chunkedPages[currentPage].size) { index ->
                    Text(
                        text = chunkedPages[currentPage][index],
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        // Display the page number at the bottom of the screen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            if(currentPage != 0) {
                Text(
                    text = "Page ${currentPage} of ${chunkedPages.size - 1}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

