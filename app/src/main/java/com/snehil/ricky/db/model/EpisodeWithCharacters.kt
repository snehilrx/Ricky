package com.snehil.ricky.db.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EpisodeWithCharacters(
    @Embedded val episode: EpisodeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val characters: List<CharacterEntity>
)