package com.example.bookreadingapp
import androidx.test.espresso.base.Default
import com.example.bookreadingapp.MainActivity
import com.example.bookreadingapp.objects.BottomNavBar
import com.example.bookreadingapp.objects.PermanentNavDrawer
import com.example.bookreadingapp.objects.TopAppBar


import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.objects.NavigationHost
import com.example.bookreadingapp.objects.Routes
import com.example.bookreadingapp.screens.ContentTable
import com.example.bookreadingapp.screens.Home
import com.example.bookreadingapp.screens.Library
import com.example.bookreadingapp.screens.Reading
import com.example.bookreadingapp.screens.Search
import com.example.bookreadingapp.utils.AdaptiveNavigationType
import org.junit.Rule
import org.junit.Test


class NavUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()


    private fun setupNavigationScreen() {
        composeTestRule.setContent {

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.Home.route) {
                composable(Routes.Home.route) {
                    Home(
                        context = ApplicationProvider.getApplicationContext(),
                        viewModel = AppViewModel(),
                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
                    )
                }
                composable(Routes.Library.route) {
                    Library(
                        context = ApplicationProvider.getApplicationContext(),
                        viewModel = AppViewModel(),
                        navController = navController,
                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
                    )
                }
                composable(Routes.Reading.route) {
                    Reading(
                        context = ApplicationProvider.getApplicationContext(),
                        viewModel = AppViewModel(),
                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
                    )
                }
                composable(Routes.Search.route) {
                    Search(
                        context = ApplicationProvider.getApplicationContext(),
                        viewModel = AppViewModel(),
                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
                    )
                }
                composable(Routes.ContentTable.route) {
                    ContentTable(
                        context = ApplicationProvider.getApplicationContext(),
                        viewModel = AppViewModel(),
                        adaptiveNavigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
                    )
                }
            }
            BottomNavBar(navController = navController, context = ApplicationProvider.getApplicationContext())
        }
    }



    @Test
    fun testBottomNavigationIsVisible() {

        setupNavigationScreen()


        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Library").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reading Mode").assertIsDisplayed()
        composeTestRule.onNodeWithText("Table of Content").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()

    }

    @Test
    fun testInitialNavigationToHomeScreen() {
        setupNavigationScreen()
        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToLibraryScreen() {

        setupNavigationScreen()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()

        composeTestRule.onNodeWithText("Library").performClick()

        composeTestRule.onNodeWithText("Library Screen").assertIsDisplayed()
    }



    @Test
    fun testNavigateToReadingScreenFromLibrary() {
        setupNavigationScreen()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Library").performClick()
        composeTestRule.onNodeWithText("The Mechanical Properties of Wood by Samuel J. Record").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Reading Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToSearchScreen() {

        setupNavigationScreen()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()

        composeTestRule.onNodeWithText("Search").performClick()

        composeTestRule.onNodeWithText("Search Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToContentTableScreen() {

        setupNavigationScreen()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()

        composeTestRule.onNodeWithText("Table of Content", ignoreCase = true).performClick()

        composeTestRule.onNodeWithText("Table of Content Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigateToReadingScreen() {

        setupNavigationScreen()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Home Screen").assertIsDisplayed()
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()

        composeTestRule.onNodeWithText("Reading Mode", ignoreCase = true).performClick()

        composeTestRule.onNodeWithText("Reading Screen").assertIsDisplayed()
    }






    @Test
    fun testLibraryScreenDisplaysCorrectly() {
        setupNavigationScreen()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Library").performClick()
        val expectedText = "Library Screen"
        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
        composeTestRule.onNodeWithText("The Mechanical Properties of Wood by Samuel J. Record").isDisplayed()

    }
    @Test
    fun testViewModelStateChange() {
        setupNavigationScreen()

        composeTestRule.onNodeWithText("Library").performClick()
        composeTestRule.onNodeWithText("Change ViewModel state").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("This state was changed from the Library screen").assertIsDisplayed()
    }


    @Test
    fun testViewModelStateChangeOnButtonClick() {
        setupNavigationScreen()

        composeTestRule.onNodeWithText("Change ViewModel state").performClick()


        composeTestRule.waitForIdle()


        composeTestRule.onNodeWithText("This state was changed from the Home screen").assertIsDisplayed()
    }


}

