package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.domain.model.User

interface GetUserUseCase {
    suspend operator fun invoke(id: String): User
}