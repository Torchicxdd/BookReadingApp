package com.example.bookreadingapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BookViewModel::class.java) -> BookViewModel(application) as T
            modelClass.isAssignableFrom(ChapterViewModel::class.java) -> ChapterViewModel(application) as T
<<<<<<< HEAD
            modelClass.isAssignableFrom(ImageViewModel::class.java) -> ImageViewModel(application) as T
            modelClass.isAssignableFrom(ParagraphViewModel::class.java) -> ParagraphViewModel(application) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
=======
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
>>>>>>> f17a6df (Add chapters view model)
