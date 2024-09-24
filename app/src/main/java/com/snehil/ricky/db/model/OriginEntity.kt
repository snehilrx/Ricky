package com.snehil.ricky.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OriginEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?,
    val url: String?
)