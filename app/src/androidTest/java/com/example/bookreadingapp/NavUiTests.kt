package com.example.bookreadingapp
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Before
    fun setUP() {
        composeTestRule.setContent {
            BookReadingAppTheme {
                BookReadingApp(
                    windowSize = WindowWidthSizeClass.Compact
                )
            }
        }
    }

    //tests that all navigation buttons are visible
    @Test
    fun testBottomNavigationIsVisible() {
        // Wait for idle state to ensure UI is rendered
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bookshelf_button").assertIsDisplayed()
    }

    //test that home screen is visible on startup
    @Test
    fun testInitialNavigationToHomeScreen() {

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
    }

    //test that library button click brings to library screen
    @Test
    fun testNavigateToLibraryScreen() {

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("library_button").performClick()

        composeTestRule.onNodeWithTag("library_screen").assertIsDisplayed()
    }

    //test that bookshelf is empty initially
    @Test
    fun testNavigateToEmptyBookShelfScreen() {
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("bookshelf_button").performClick()

        composeTestRule.onNodeWithTag("no_books_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("empty_bookshelf_text").assertIsDisplayed()

    }

//test that clicking book in library downloads it
    @Test
    fun testLibraryScreenBookClicking() {

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_button").performClick()
        composeTestRule.onNodeWithTag("library_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131755140").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131755140").performClick()
        composeTestRule.onNodeWithTag("book_item_2131755140").assertIsNotDisplayed()

    }

//test that downloaded book is displayed on bookshelf
    @Test
    fun testNavigateToBookShelfScreenWithBook() {
        composeTestRule.waitForIdle()
        testLibraryScreenBookClicking()
        composeTestRule.onNodeWithTag("bookshelf_button").performClick()
        composeTestRule.onNodeWithTag("bookshelf_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131755140").assertIsDisplayed()
    }

    //test that clicking bookshelf book navigates to table of contents screen
    @Test
    fun testNavigateToTableOfContentScreenOnBookClick() {
        composeTestRule.waitForIdle()
        testNavigateToBookShelfScreenWithBook()
        composeTestRule.onNodeWithTag("book_item_2131755140").performClick()
        composeTestRule.onNodeWithTag("content_screen").assertIsDisplayed()
    }



//test that reading button navigates to reading screen
    @Test
    fun testNavigateToReadingScreenFromTableContent() {
        testNavigateToTableOfContentScreenOnBookClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("go_to_reading_button").performClick()
        composeTestRule.onNodeWithTag("reading_screen").assertIsDisplayed()
    }

//test that reading mode works and removes navbar
    @Test
    fun testReadingModeButton() {
        testNavigateToReadingScreenFromTableContent()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bookshelf_button").assertIsDisplayed()

        composeTestRule.onNodeWithTag("reading_mode_button").performClick()

        composeTestRule.onNodeWithTag("home_button").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("library_button").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("bookshelf_button").assertIsNotDisplayed()

    }


//test that search button navigates to search screen
    @Test
    fun testNavigateToSearchScreen() {
        testNavigateToTableOfContentScreenOnBookClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("go_to_search_button").performClick()

        composeTestRule.onNodeWithTag("search_screen").assertIsDisplayed()
    }

}