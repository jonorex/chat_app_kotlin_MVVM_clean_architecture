package com.joao.garageapp.domain.usecase.user


import com.joao.garageapp.data.user.UserRepository
import javax.inject.Inject

class CreateUserAuthImpl @Inject constructor(
    private val userRepository: UserRepository
        ): CreateUserAuth {

    override suspend fun invoke(email: String, password: String): String? {
        return userRepository.createUserAuth(email, password)
    }
}