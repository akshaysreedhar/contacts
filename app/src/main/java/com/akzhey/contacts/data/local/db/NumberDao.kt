package com.akzhey.contacts.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface NumberDao {
    @Upsert
    suspend fun insertNumber(numberItem: NumberItem)

    @Query("SELECT * FROM number")
    suspend fun getAllNumbers(): List<NumberItem>

    @Query("DELETE FROM number")
    suspend fun dropTable()
}