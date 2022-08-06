package com.joao.garageapp.domain.usecase.user

import com.google.firebase.auth.FirebaseUser

interface CreateUserAuth {
    suspend operator fun invoke(email: String, password: String): String?
}