package com.snehil.ricky.di

import android.content.Context
import androidx.room.Room
import com.snehil.ricky.Constants.DB_NAME
import com.snehil.ricky.db.CharacterDao
import com.snehil.ricky.db.RickyDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RickyDb {
        return Room.databaseBuilder(context, RickyDb::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCharacterDao(db: RickyDb): CharacterDao {
        return db.characterDao()
    }

}
