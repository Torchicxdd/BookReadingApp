package com.example.bookreadingapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookreadingapp.data.BooksAppRoomDatabase
import com.example.bookreadingapp.data.entities.Table
import com.example.bookreadingapp.data.repositories.TableRepository

class TableViewModel(application: Application) : ViewModel() {
    val searchedResults: MutableLiveData<List<Table>>
    private val repository: TableRepository

    init {
        val tableDb = BooksAppRoomDatabase.getInstance(application)
        val tableDao = tableDb.tableDao()
        repository = TableRepository(tableDao)

        searchedResults = repository.searchResults
    }

    suspend fun insertTable(table: Table) {
        repository.insertTable(table)
    }

    fun deleteTable(id: Int) {
        repository.deleteTable(id)
    }

    fun findTableById(id: Int) {
        repository.findTableById(id)
    }

    fun findTableByChapterId(chapterId: Int) {
        repository.findTableByChapterId(chapterId)
    }

    fun findTablesInAscOrder(chapterId: Int) {
        repository.findTablesInAscOrder(chapterId)
    }
}