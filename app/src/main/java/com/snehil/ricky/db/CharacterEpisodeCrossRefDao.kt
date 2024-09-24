package com.snehil.ricky.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.snehil.ricky.db.model.CharacterEpisodeCrossRef

@Dao
interface CharacterEpisodeCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characterEpisodeCrossRef: CharacterEpisodeCrossRef)
}
