package com.akzhey.contacts.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [NumberItem::class])
abstract class NumberDatabase: RoomDatabase() {
    abstract fun numberDao(): NumberDao
}