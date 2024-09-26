package com.snehil.ricky.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) override val characterId: Int,
    override val name: String? = null,
    override val status: String? = null,
    override val species: String? = null,
    override val type: String? = null,
    override val gender: String? = null,
    override val originName: String? = null,
    override val locationName: String? = null,
    override val image: String? = null,
    override val firstEpisodeUrl: String? = null,
    override val created: String? = null,
    override val pageNo: Int = 0,
    val isFavourite: Boolean? = null,
) : PartialCharacterEntity(
    characterId,
    name,
    status,
    species,
    type,
    gender,
    originName,
    locationName,
    image,
    firstEpisodeUrl,
    created,
    pageNo,
)

open class PartialCharacterEntity(
    @PrimaryKey(autoGenerate = true) open val characterId: Int,
    open val name: String? = null,
    open val status: String? = null,
    open val species: String? = null,
    open val type: String? = null,
    open val gender: String? = null,
    open val originName: String? = null,
    open val locationName: String? = null,
    open val image: String? = null,
    open val firstEpisodeUrl: String? = null,
    open val created: String? = null,
    open val pageNo: Int = 0
)

