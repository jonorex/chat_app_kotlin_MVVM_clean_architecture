package com.joao.garageapp.data.user

import com.joao.garageapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DataStoreSource {
    suspend fun saveCurrentUser(user: User) : User

    suspend fun getCurrentUser(): Flow<User>

    suspend fun clearDataStore()

}