package com.example.bookreadingapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    var exampleState by mutableStateOf("This is the example state before changing")

    fun updateExampleState(newText: String) {
        exampleState = newText
    }
}
