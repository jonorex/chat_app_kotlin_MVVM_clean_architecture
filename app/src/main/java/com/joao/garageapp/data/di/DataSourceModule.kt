package com.joao.garageapp.data.di

import com.joao.garageapp.data.chat.ChatDataSource
import com.joao.garageapp.data.chat.FirebaseChatDataSource
import com.joao.garageapp.data.user.FirebaseUserRepository
import com.joao.garageapp.data.user.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindUserDataSource(dataSource: FirebaseUserRepository): UserDataSource

    @Singleton
    @Binds
    fun bindChatDataSource(dataSource: FirebaseChatDataSource): ChatDataSource
}