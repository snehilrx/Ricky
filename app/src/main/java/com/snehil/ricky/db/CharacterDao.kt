package com.snehil.ricky.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.db.model.CharacterWithEpisodes
import com.snehil.ricky.db.model.EpisodeWithCharacters

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Transaction
    @Query("SELECT * FROM CharacterEntity")
    suspend fun getAllCharacters(): List<CharacterWithEpisodes>

    @Transaction
    @Query("SELECT * FROM EpisodeEntity")
    suspend fun getAllEpisodesWithCharacters(): List<EpisodeWithCharacters>
}