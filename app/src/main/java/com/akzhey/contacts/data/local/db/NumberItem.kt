package com.akzhey.contacts.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number")
data class NumberItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val number: String,
    val numberType: String,
)