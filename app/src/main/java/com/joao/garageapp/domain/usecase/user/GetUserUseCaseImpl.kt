package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.data.user.UserRepository
import com.joao.garageapp.domain.model.User
import javax.inject.Inject

class GetUserUseCaseImpl @Inject constructor (
     private val userRepository: UserRepository
        ): GetUserUseCase{

    override suspend fun invoke(id: String): User {
        return userRepository.getUser(id)
    }
}