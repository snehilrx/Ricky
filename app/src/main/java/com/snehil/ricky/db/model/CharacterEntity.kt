package com.snehil.ricky.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val originId: Int?,
    val locationId: Int?,
    val image: String?,
    val url: String?,
    val created: String?
)

