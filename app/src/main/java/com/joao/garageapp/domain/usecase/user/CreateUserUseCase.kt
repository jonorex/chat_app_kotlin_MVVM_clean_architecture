package com.joao.garageapp.domain.usecase.user

import android.content.Context
import android.net.Uri
import com.joao.garageapp.domain.model.User

interface CreateUserUseCase {
    suspend operator fun invoke(name: String, image: Uri, email: String, password: String): User
}