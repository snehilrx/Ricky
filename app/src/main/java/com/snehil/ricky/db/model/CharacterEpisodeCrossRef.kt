package com.snehil.ricky.db.model

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "episodeId"])
data class CharacterEpisodeCrossRef(
    val characterId: Int,
    val episodeId: Int
)