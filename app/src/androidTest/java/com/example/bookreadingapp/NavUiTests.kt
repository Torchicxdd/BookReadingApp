package com.example.bookreadingapp
import androidx.test.espresso.base.Default
import com.example.bookreadingapp.MainActivity
import com.example.bookreadingapp.objects.BottomNavBar
import com.example.bookreadingapp.objects.PermanentNavDrawer
import com.example.bookreadingapp.objects.TopAppBar


import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.objects.NavigationHost
import com.example.bookreadingapp.objects.Routes
import com.example.bookreadingapp.screens.ContentTable
import com.example.bookreadingapp.screens.Home
import com.example.bookreadingapp.screens.Library
import com.example.bookreadingapp.screens.Reading
import com.example.bookreadingapp.screens.Search
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import com.example.bookreadingapp.utils.AdaptiveNavigationType
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

