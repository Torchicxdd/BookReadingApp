package com.example.bookreadingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.bookreadingapp.objects.AppViewModel
import com.example.bookreadingapp.objects.BottomNavBar
import com.example.bookreadingapp.objects.NavigationHost
import com.example.bookreadingapp.ui.theme.BookReadingAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookReadingAppTheme {
                BookReadingApp()
            }
        }
    }
}

@Composable
fun BookReadingApp(viewModel: AppViewModel = viewModel())  {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        content = {padding ->
            Column(Modifier.padding(padding)) {
                NavigationHost(
                    navController,
                    context
                )
            } },
        bottomBar = {
            if(!viewModel.readingMode) {
                BottomNavBar(navController, context)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BookReadingAppTheme {
        BookReadingApp()
    }
}