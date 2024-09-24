package com.snehil.ricky.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?,
    val type: String?,
    val dimension: String?,
    val url: String?,
    val created: String?
)