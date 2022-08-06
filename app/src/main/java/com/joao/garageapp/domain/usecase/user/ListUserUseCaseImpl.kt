package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.data.user.UserRepository
import com.joao.garageapp.domain.model.User
import javax.inject.Inject

class ListUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : ListUsersUseCase {
    override suspend fun invoke(): List<User> {
        return  userRepository.listUser()
    }
}