package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.domain.model.User

interface ListUsersUseCase {
    suspend operator fun invoke(): List<User>
}