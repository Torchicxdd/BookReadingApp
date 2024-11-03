package com.example.bookreadingapp.objects

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class AppViewModel() : ViewModel() {
    var exampleState by mutableStateOf("This is the state before being changed")

    fun updateExampleState(newText: String) {
        exampleState = newText
    }
}
