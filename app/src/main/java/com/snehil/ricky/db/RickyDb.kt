package com.snehil.ricky.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.db.model.EpisodeNameEntity

@Database(
    entities = [CharacterEntity::class, EpisodeNameEntity::class],
    version = 1,
    exportSchema = true
)
abstract class RickyDb : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}