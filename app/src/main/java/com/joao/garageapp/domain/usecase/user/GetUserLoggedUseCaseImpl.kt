package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.data.user.DataStoreSource
import com.joao.garageapp.data.user.UserRepository
import com.joao.garageapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLoggedUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetUserLoggedUseCase {
    override suspend fun invoke(): Flow<User> {
        return userRepository.getUserLogged()
    }
}