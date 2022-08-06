package com.joao.garageapp.data.di

import com.joao.garageapp.data.user.DataSourceImpl
import com.joao.garageapp.data.user.DataStoreSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StoreModule {
    @Singleton
    @Binds
    fun bindDataStore(dataStoreSource: DataSourceImpl): DataStoreSource
}