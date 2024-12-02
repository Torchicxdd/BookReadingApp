package com.example.bookreadingapp.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bookreadingapp.data.entities.Table

@Dao
interface TableDao {
    @Insert
    fun insertTable(table: Table)

    @Query("SELECT * FROM tables WHERE tableId = :id")
    fun findTableById(id: Int): List<Table>

    @Query("SELECT * FROM tables WHERE chapterId = :chapterId")
    fun findTableByChapterId(chapterId: Int): List<Table>

    @Query("DELETE FROM tables WHERE tableId = :id")
    fun deleteTable(id: Int)

    @Query("SELECT * FROM tables WHERE chapterId = :chapterId ORDER BY tablePosition ASC")
    fun findTablesInAscOrder(chapterId: Int): List<Table>
}