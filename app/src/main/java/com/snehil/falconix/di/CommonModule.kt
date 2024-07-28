package com.snehil.falconix.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.snehil.falconix.api.model.Rocket
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }
}