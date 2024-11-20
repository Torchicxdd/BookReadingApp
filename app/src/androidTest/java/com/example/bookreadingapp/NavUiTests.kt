package com.example.bookreadingapp

import android.content.Context
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bookreadingapp.objects.Routes
import com.example.bookreadingapp.download.FileDownload
import com.example.bookreadingapp.ui.DownloadViewModel
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock


@RunWith(AndroidJUnit4::class)
class NavUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()
    private var contextMock: Context = mock()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUP() {
        val repository = FileDownload(contextMock)
        val downloadViewModel = DownloadViewModel(repository)

        composeTestRule.setContent {
            // Setup test navigator
            // https://github.com/google-developer-training/basic-android-kotlin-compose-training-cupcake/blob/main/app/src/androidTest/java/com/example/cupcake/test/CupcakeScreenNavigationTest.kt
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            BookReadingAppTheme {
                BookReadingApp(
                    windowSize = WindowWidthSizeClass.Compact,
                    navController = navController,
                    downloadViewModel = downloadViewModel
                )
            }
        }
    }

    private fun navigateToLibrary() {
        composeTestRule.onNodeWithTag("library_button").performClick()
    }

    private fun navigateToBookshelf() {
        composeTestRule.onNodeWithTag("bookshelf_button").performClick()
    }

    private fun clickLibraryBook() {
        navigateToLibrary()
        composeTestRule.onNodeWithTag("book_item_2131755163").performClick()
    }

    private fun clickBookshelfBook() {
        navigateToBookshelf()
        composeTestRule.onNodeWithTag("book_item_2131755163").performClick()
    }

    private fun navigateToTableOfContents() {
        clickLibraryBook()
        clickBookshelfBook()
    }

    private fun navigateToReadingScreen() {
        composeTestRule.onNodeWithTag("go_to_reading_button").performClick()
    }

    private fun navigateToSearchScreen() {
        composeTestRule.onNodeWithTag("go_to_search_button").performClick()
    }

    private fun performNavigateUp() {
        composeTestRule.onNodeWithTag("Back_Button").performClick()
    }

    //tests that all navigation buttons are visible
    @Test
    fun testBottomNavigationIsVisible() {
        // Wait for idle state to ensure UI is rendered
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("nav_bar").assertIsDisplayed()
        composeTestRule.onNodeWithTag("home_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bookshelf_button").assertIsDisplayed()
    }

    //test that home screen is visible on startup
    @Test
    fun testInitialNavigationToHomeScreen() {

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Back_Button").assertIsNotDisplayed()
        navController.assertCurrentRouteName(Routes.Home.route)
    }

    //test that library button click brings to library screen
    @Test
    fun testNavigateToLibraryScreen() {

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        navigateToLibrary()
        composeTestRule.onNodeWithTag("library_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Back_Button").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.Library.route)
    }

    //test that bookshelf is empty initially
    @Test
    fun testNavigateToEmptyBookShelfScreen() {
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        navigateToBookshelf()
        composeTestRule.onNodeWithTag("no_books_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("empty_bookshelf_text").assertIsDisplayed()
    }

//test that clicking book in library downloads it
    @Test
    fun testLibraryScreenBookClicking() {

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        navigateToLibrary()
        composeTestRule.onNodeWithTag("library_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131755163").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131755163").performClick()
        composeTestRule.onNodeWithTag("book_item_2131755163").assertIsNotDisplayed()
    }

//test that downloaded book is displayed on bookshelf
    @Test
    fun testNavigateToBookShelfScreenWithBook() {
        composeTestRule.waitForIdle()
        clickLibraryBook()
        navigateToBookshelf()
        composeTestRule.onNodeWithTag("bookshelf_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131755163").assertIsDisplayed()
    }

    //test that clicking bookshelf book navigates to table of contents screen
    @Test
    fun testNavigateToTableOfContentScreenOnBookClick() {
        composeTestRule.waitForIdle()
        clickLibraryBook()
        clickBookshelfBook()
        composeTestRule.onNodeWithTag("content_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Back_Button").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.ContentTable.route)
    }

    //test that reading button navigates to reading screen
    @Test
    fun testNavigateToReadingScreenFromTableContent() {
        navigateToTableOfContents()
        composeTestRule.waitForIdle()

        navigateToReadingScreen()
        composeTestRule.onNodeWithTag("reading_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Back_Button").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.Reading.route)
    }

    //test that reading mode works and removes navbar
    @Test
    fun testReadingModeButton() {
        navigateToTableOfContents()
        navigateToReadingScreen()
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
        navigateToTableOfContents()
        navigateToSearchScreen()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("search_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Back_Button").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.Search.route)
    }

    //-------- Navigation with Up button
    @Test
    fun appNavHost_clickBackLibrary_navigatesToHomeScreen() {
        navigateToLibrary()
        performNavigateUp()
        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.Home.route)
    }

    @Test
    fun appNavHost_clickBackBookshelf_navigatesToHomeScreen() {
        navigateToBookshelf()
        performNavigateUp()
        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.Home.route)
    }

    @Test
    fun appNavHost_clickBackTableOfContents_navigatesToBookShelf() {
        navigateToTableOfContents()
        performNavigateUp()
        composeTestRule.onNodeWithTag("bookshelf_screen").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.Bookshelf.route)
    }

    @Test
    fun appNavHost_clickBackSearch_navigatesToTableOfContents() {
        navigateToTableOfContents()
        navigateToSearchScreen()
        performNavigateUp()
        composeTestRule.onNodeWithTag("content_screen").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.ContentTable.route)
    }

    @Test
    fun appNavHost_clickBackReading_navigatesToTableOfContents() {
        navigateToTableOfContents()
        navigateToReadingScreen()
        performNavigateUp()
        composeTestRule.onNodeWithTag("content_screen").assertIsDisplayed()
        navController.assertCurrentRouteName(Routes.ContentTable.route)
    }
}