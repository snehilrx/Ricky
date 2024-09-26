package com.snehil.ricky.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Upsert
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.db.model.EpisodeNameEntity
import com.snehil.ricky.db.model.PartialCharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Upsert(entity = CharacterEntity::class)
    suspend fun insertAll(characters: List<PartialCharacterEntity>)


    @Query("UPDATE CharacterEntity SET isFavourite = (NOT isFavourite OR isFavourite IS NULL) WHERE characterId = :id")
    suspend fun toggleFavourite(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodeName(episode: EpisodeNameEntity)

    @Query(
        """
         SELECT *  FROM CharacterEntity
    """
    )
    @RewriteQueriesToDropUnusedColumns
    fun getAllCharacters(): PagingSource<Int, CharacterEntity>


    @Query(
        """
         SELECT *  FROM CharacterEntity where characterId = :id
    """
    )
    @RewriteQueriesToDropUnusedColumns
    fun getCharacter(id: Int): Flow<CharacterEntity>

    @Query(
        """
         SELECT *  FROM CharacterEntity WHERE name LIKE :query
    """
    )
    @RewriteQueriesToDropUnusedColumns
    fun getAllCharacters(query: String): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM EpisodeNameEntity WHERE id = :episodeId")
    suspend fun getEpisodeName(episodeId: Int?): EpisodeNameEntity?

    @Query("SELECT * FROM EpisodeNameEntity WHERE id = :episodeId")
    fun getEpisodeNameFlow(episodeId: Int?): Flow<EpisodeNameEntity?>

}