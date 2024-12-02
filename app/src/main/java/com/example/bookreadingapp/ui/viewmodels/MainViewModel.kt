package com.example.bookreadingapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val bookViewModel: BookViewModel = BookViewModel(application)
    val chapterViewModel: ChapterViewModel = ChapterViewModel(application)

}