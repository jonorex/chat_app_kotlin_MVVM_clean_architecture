package com.joao.garageapp.data.di

import android.content.Context
import com.joao.garageapp.data.user.DataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Singleton
    @Provides
    fun  provideDataStoreRepository(@ApplicationContext context: Context) = DataSourceImpl(context)
}