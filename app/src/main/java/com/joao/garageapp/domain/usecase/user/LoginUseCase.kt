package com.joao.garageapp.domain.usecase.user

import com.google.firebase.auth.FirebaseUser
import com.joao.garageapp.domain.model.User

interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): User?
}