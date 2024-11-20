package com.example.bookreadingapp

import com.example.bookreadingapp.ui.viewmodels.AppViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppViewModelTest {
    private lateinit var viewModel: AppViewModel

    @Before
    // Initializing the ViewModel before each test
    fun setup() {
        viewModel = AppViewModel()
    }

    @Test
    // Testing that the library books are initialized and not empty after the library is set up
    fun initializeLibrary_libraryBooksIsNotEmpty() {
        assertTrue(viewModel.libraryBooks.isNotEmpty())
    }

    @Test
    // Testing that the moving the books from the library to the bookshelf is done correctly
    fun moveBookToBookshelf_bookIsMovedFromLibraryToBookshelf() {
        // Move a book from the library to the bookshelf
        val bookToMove = viewModel.libraryBooks[0]
        viewModel.moveBookToBookshelf(bookToMove)

        // Ensuring that the book was removed from the library and added to the bookshelf
        assertFalse(viewModel.libraryBooks.contains(bookToMove))
        assertTrue(viewModel.bookshelfBooks.contains(bookToMove))
    }

    @Test
    // Testing that the reading mode is toggled correctly (false to true, and back to false)
    fun updateReadingMode_readingModeIsToggledCorrectly() {
        viewModel.updateReadingMode()
        assertTrue(viewModel.readingMode)

        viewModel.updateReadingMode()
        assertFalse(viewModel.readingMode)
    }

    @Test
    // Testing that the selected book title resId is updated correctly
    fun updateBookTitle_titleResIdIsUpdatedCorrectly() {
        val newResId = 123
        viewModel.updateBookTitle(newResId)
        assertEquals(newResId, viewModel.selectedBookTitleResId)
    }

    @Test
    // Testing that the search bar input is updated correctly
    fun updateSearchBarInput_inputIsUpdatedCorrectly() {
        val newInput = "Kotlin"
        viewModel.updateSearchBarInput(newInput)
        assertEquals(newInput, viewModel.searchBarInput)
    }

    @Test
    // Testing that the search result text is updated when the input is not blank
    fun performSearch_searchResultTextIsUpdatedWhenInputIsNotBlank() {
        viewModel.updateSearchBarInput("Kotlin")
        viewModel.performSearch()
        assertEquals("Searching for the word Kotlin", viewModel.searchResultText)
    }

    @Test
    // Testing that no search result text is displayed when the input is blank
    fun performSearch_searchResultTextRemainsEmptyWhenInputIsBlank() {
        viewModel.updateSearchBarInput("")
        viewModel.performSearch()
        assertEquals("", viewModel.searchResultText)
    }
}