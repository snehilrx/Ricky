package com.snehil.ricky.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?,
    val airDate: String?,
    val episode: String?,
    val url: String?,
    val created: String?
)