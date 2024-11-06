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


//    private fun setupNavigationScreen() {
//        composeTestRule.setContent {
//
//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = Routes.Home.route) {
//                composable(Routes.Home.route) {
//                    Home(
//                        context = ApplicationProvider.getApplicationContext(),
//                        viewModel = AppViewModel(),
//                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION,
//                        modifier = Modifier.testTag("home_screen")
//                    )
//                }
//                composable(Routes.Library.route) {
//                    Library(
//                        context = ApplicationProvider.getApplicationContext(),
//                        viewModel = AppViewModel(),
//                        navController = navController,
//                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
//                    )
//                }
//                composable(Routes.Reading.route) {
//                    Reading(
//                        context = ApplicationProvider.getApplicationContext(),
//                        viewModel = AppViewModel(),
//                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
//                    )
//                }
//                composable(Routes.Search.route) {
//                    Search(
//                        context = ApplicationProvider.getApplicationContext(),
//                        viewModel = AppViewModel(),
//                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
//                    )
//                }
//                composable(Routes.ContentTable.route) {
//                    ContentTable(
//                        context = ApplicationProvider.getApplicationContext(),
//                        viewModel = AppViewModel(),
//                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
//                    )
//                }
//            }
//            BottomNavBar(navController = navController, context = ApplicationProvider.getApplicationContext())
//        }
//    }

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
        //composeTestRule.onNodeWithTag("bottom_nav_bar").assertIsDisplayed()

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

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()

        composeTestRule.onNodeWithText("Library").performClick()

        composeTestRule.onNodeWithText("Library Screen").assertIsDisplayed()
    }



    @Test
    fun testNavigateToReadingScreenFromLibrary() {


        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Library").performClick()
        composeTestRule.onNodeWithText("The Mechanical Properties of Wood by Samuel J. Record").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Reading Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToSearchScreen() {



        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()

        composeTestRule.onNodeWithText("Search").performClick()

        composeTestRule.onNodeWithText("Search Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToContentTableScreen() {



        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()

        composeTestRule.onNodeWithText("Table of Content", ignoreCase = true).performClick()

        composeTestRule.onNodeWithText("Table of Content Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToReadingScreen() {



        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()

        composeTestRule.onNodeWithText("Reading Mode", ignoreCase = true).performClick()

        composeTestRule.onNodeWithText("Reading Screen").assertIsDisplayed()
    }






    @Test
    fun testLibraryScreenDisplaysCorrectly() {

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Library").performClick()
        val expectedText = "Library Screen"
        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
        composeTestRule.onNodeWithText("The Mechanical Properties of Wood by Samuel J. Record").isDisplayed()

    }
    @Test
    fun testViewModelStateChange() {


        composeTestRule.onNodeWithText("Library").performClick()
        composeTestRule.onNodeWithText("Change ViewModel state").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("This state was changed from the Library screen").assertIsDisplayed()
    }


    @Test
    fun testViewModelStateChangeOnButtonClick() {


        composeTestRule.onNodeWithText("Change ViewModel state").performClick()


        composeTestRule.waitForIdle()


        composeTestRule.onNodeWithText("This state was changed from the Home screen").assertIsDisplayed()
    }


}

