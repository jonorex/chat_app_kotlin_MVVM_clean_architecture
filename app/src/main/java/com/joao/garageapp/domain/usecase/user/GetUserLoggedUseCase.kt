package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.domain.model.User
import kotlinx.coroutines.flow.Flow


interface GetUserLoggedUseCase {
    suspend operator fun invoke(): Flow<User>
}