package com.snehil.ricky.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.snehil.ricky.db.model.EpisodeEntity

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episode: EpisodeEntity)

    @Query("SELECT * FROM EpisodeEntity")
    suspend fun getAllEpisodes(): List<EpisodeEntity>
}