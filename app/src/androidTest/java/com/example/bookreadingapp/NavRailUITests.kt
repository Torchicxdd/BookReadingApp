package com.example.bookreadingapp

import android.app.Application
import android.content.Context
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.bookreadingapp.data.download.FileDownload
import com.example.bookreadingapp.ui.BookReadingApp
import com.example.bookreadingapp.ui.viewmodels.DownloadViewModel
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme
import com.example.bookreadingapp.ui.viewmodels.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class NavRailUITests {
    @get:Rule
    val composeTestRule = createComposeRule()
    private var contextMock: Context = mock()
    private lateinit var navController: TestNavHostController
    val applicationMock = mock<Application>()

    @Before
    fun setUp() {
        whenever(applicationMock.applicationContext).thenReturn(contextMock)
        val repository = FileDownload(contextMock)
        val downloadViewModel = DownloadViewModel(repository)
        val mockMainViewModel = MainViewModel(applicationMock)

        composeTestRule.setContent {
            // Setup test navigator
            // https://github.com/google-developer-training/basic-android-kotlin-compose-training-cupcake/blob/main/app/src/androidTest/java/com/example/cupcake/test/CupcakeScreenNavigationTest.kt
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            BookReadingAppTheme {
                BookReadingApp(
                    windowSize = WindowWidthSizeClass.Medium,
                    navController = navController,
                    downloadViewModel = downloadViewModel,
                    mainViewModel = mockMainViewModel
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