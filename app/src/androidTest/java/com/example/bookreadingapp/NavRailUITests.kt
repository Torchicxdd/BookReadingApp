package com.example.bookreadingapp

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavRailUITests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            // Setup test navigator
            // https://github.com/google-developer-training/basic-android-kotlin-compose-training-cupcake/blob/main/app/src/androidTest/java/com/example/cupcake/test/CupcakeScreenNavigationTest.kt
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            BookReadingAppTheme {
                BookReadingApp(
                    windowSize = WindowWidthSizeClass.Medium,
                    navController = navController
                )
            }
        }
    }

    @Test
    fun testNavRailIsVisisble() {
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("nav_rail").assertIsDisplayed()
        composeTestRule.onNodeWithTag("home_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("library_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("bookshelf_button").assertIsDisplayed()
    }
}