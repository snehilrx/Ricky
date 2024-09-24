package com.snehil.ricky.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.db.model.CharacterEpisodeCrossRef
import com.snehil.ricky.db.model.EpisodeEntity
import com.snehil.ricky.db.model.LocationEntity
import com.snehil.ricky.db.model.OriginEntity

@Database(
    entities = [CharacterEntity::class,CharacterEpisodeCrossRef::class, EpisodeEntity::class, LocationEntity::class, OriginEntity::class],
    version = 1,
    exportSchema = true
)
abstract class RickyDb : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun originDao(): OriginDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun characterDao(): CharacterDao
    abstract fun characterEpisodeCrossRefDao(): CharacterEpisodeCrossRefDao
}