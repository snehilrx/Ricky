package com.snehil.ricky.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CharacterWithEpisodes(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val episodes: List<EpisodeEntity>
)