package com.example.bookreadingapp


import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.bookreadingapp.download.DownloadViewModel
import com.example.bookreadingapp.objects.DownloadViewModelFactory
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val downloadViewModel: DownloadViewModel by viewModels {
        DownloadViewModelFactory(this.applicationContext) // Use application context to prevent memory leaks
    }

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


    @Test
    fun testBottomNavigationIsVisible() {
        // Wait for idle state to ensure UI is rendered
        composeTestRule.waitForIdle()

        // Assert that the Home button is visible
        composeTestRule.onNodeWithTag("home_button").assertIsDisplayed()

        // Assert that the Library button is visible
        composeTestRule.onNodeWithTag("library_button").assertIsDisplayed()

        // Assert that the Search button is visible
        composeTestRule.onNodeWithTag("search_button").assertIsDisplayed()

        // Assert that the Content Table button is visible
        composeTestRule.onNodeWithTag("content_button").assertIsDisplayed()

        // Assert that the Reading button is visible
        composeTestRule.onNodeWithTag("reading_button").assertIsDisplayed()
    }

    @Test
    fun testInitialNavigationToHomeScreen() {

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToLibraryScreen() {



        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("library_button").performClick()

        composeTestRule.onNodeWithTag("library_screen").assertIsDisplayed()
    }



    @Test
    fun testNavigateToReadingScreenFromLibrary() {


        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_button").performClick()
        composeTestRule.onNodeWithTag("book_item_2131689622").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131689622").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("reading_screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToSearchScreen() {



        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("search_button").performClick()

        composeTestRule.onNodeWithTag("search_screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToContentTableScreen() {



        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("content_button").performClick()

        composeTestRule.onNodeWithTag("content_screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToReadingScreen() {



        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()

        composeTestRule.onNodeWithTag("reading_button").performClick()

        composeTestRule.onNodeWithTag("reading_screen").assertIsDisplayed()
    }






    @Test
    fun testLibraryScreenDisplaysCorrectly() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_button").performClick()
        composeTestRule.onNodeWithTag("library_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("book_item_2131689622").assertIsDisplayed()

    }


    @Test
    fun testViewModelStateChangeOnButtonClickInHome() {

        composeTestRule.onNodeWithTag("home_viewmodel_text")
            .assertTextEquals("This is the state before being changed")

        composeTestRule.onNodeWithTag("home_viewmodel_button").performClick()

        composeTestRule.onNodeWithTag("home_viewmodel_text")
            .assertTextEquals("This state was changed from the Home screen")

        composeTestRule.onNodeWithTag("library_button").performClick()
        composeTestRule.onNodeWithTag("library_viewmodel_text")
            .assertTextEquals("This state was changed from the Home screen")


    }

    @Test
    fun testViewModelStateChangeOnButtonClickInLibrary() {

        composeTestRule.onNodeWithTag("library_button").performClick()
        composeTestRule.onNodeWithTag("library_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_viewmodel_text")
            .assertTextEquals("This is the state before being changed")

        composeTestRule.onNodeWithTag("library_viewmodel_button").performClick()

        composeTestRule.onNodeWithTag("library_viewmodel_text")
            .assertTextEquals("This state was changed from the Library screen")

        composeTestRule.onNodeWithTag("home_button").performClick()
        composeTestRule.onNodeWithTag("home_viewmodel_text")
            .assertTextEquals("This state was changed from the Library screen")
    }

    @Test
    fun testViewModelStateChangeOnButtonClickInReading() {

        composeTestRule.onNodeWithTag("reading_button").performClick()
        composeTestRule.onNodeWithTag("reading_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("reading_viewmodel_text")
            .assertTextEquals("This is the state before being changed")

        composeTestRule.onNodeWithTag("reading_viewmodel_button").performClick()

        composeTestRule.onNodeWithTag("reading_viewmodel_text")
            .assertTextEquals("This state was changed from the Reading screen")

        composeTestRule.onNodeWithTag("home_button").performClick()
        composeTestRule.onNodeWithTag("home_viewmodel_text")
            .assertTextEquals("This state was changed from the Reading screen")
    }

    @Test
    fun testViewModelStateChangeOnButtonClickInSearch() {

        composeTestRule.onNodeWithTag("search_button").performClick()
        composeTestRule.onNodeWithTag("search_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("search_viewmodel_text")
            .assertTextEquals("This is the state before being changed")

        composeTestRule.onNodeWithTag("search_viewmodel_button").performClick()

        composeTestRule.onNodeWithTag("search_viewmodel_text")
            .assertTextEquals("This state was changed from the Search screen")

        composeTestRule.onNodeWithTag("home_button").performClick()
        composeTestRule.onNodeWithTag("home_viewmodel_text")
            .assertTextEquals("This state was changed from the Search screen")
    }

    @Test
    fun testViewModelStateChangeOnButtonClickInContentTable() {

        composeTestRule.onNodeWithTag("content_button").performClick()
        composeTestRule.onNodeWithTag("content_screen").assertIsDisplayed()
        composeTestRule.onNodeWithTag("content_viewmodel_text")
            .assertTextEquals("This is the state before being changed")

        composeTestRule.onNodeWithTag("content_viewmodel_button").performClick()

        composeTestRule.onNodeWithTag("content_viewmodel_text")
            .assertTextEquals("This state was changed from the Content screen")

        composeTestRule.onNodeWithTag("home_button").performClick()
        composeTestRule.onNodeWithTag("home_viewmodel_text")
            .assertTextEquals("This state was changed from the Content screen")
    }




}

