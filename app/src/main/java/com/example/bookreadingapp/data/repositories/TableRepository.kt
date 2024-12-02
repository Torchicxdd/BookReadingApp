package com.example.bookreadingapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.example.bookreadingapp.data.daos.TableDao
import com.example.bookreadingapp.data.entities.Image
import com.example.bookreadingapp.data.entities.Table
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TableRepository(private val tableDao: TableDao) {
    val searchResults = MutableLiveData<List<Table>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun insertTable(newTable: Table) {
        coroutineScope.launch(Dispatchers.IO) {
            tableDao.insertTable(newTable)
        }
    }

    fun deleteTable(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            tableDao.deleteTable(id)
        }
    }

    fun findTableById(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindTableById(id).await()
        }
    }

    private fun asyncFindTableById(id: Int) : Deferred<List<Table>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async tableDao.findTableById(id)
        }

    fun findTableByChapterId(chapterId: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindTableByChapterId(chapterId).await()
        }
    }

    private fun asyncFindTableByChapterId(chapterId: Int) : Deferred<List<Table>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async tableDao.findTableByChapterId(chapterId)
        }

    fun findTablesInAscOrder(chapterId: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindTablesInAscOrder(chapterId).await()
        }
    }

    private fun asyncFindTablesInAscOrder(chapterId: Int) : Deferred<List<Table>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async tableDao.findTablesInAscOrder(chapterId)
        }
}