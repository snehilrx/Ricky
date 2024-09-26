package com.snehil.ricky.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeNameEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
)