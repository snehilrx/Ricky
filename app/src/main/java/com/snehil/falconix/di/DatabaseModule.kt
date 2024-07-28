package com.snehil.falconix.di

import android.content.Context
import androidx.room.Room
import com.snehil.falconix.Constraints.DB_NAME
import com.snehil.falconix.db.FalconIXDb
import com.snehil.falconix.db.LaunchDao
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
    fun provideDatabase(@ApplicationContext context: Context): FalconIXDb {
        return Room.databaseBuilder(context, FalconIXDb::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLaunchDao(appDatabase: FalconIXDb): LaunchDao {
        return appDatabase.launchDao()
    }
}
