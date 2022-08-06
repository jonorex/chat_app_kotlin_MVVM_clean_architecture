package com.joao.garageapp.data.user

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.joao.garageapp.domain.model.User

interface UserDataSource {
    suspend fun uploadUserImage(userImg: Uri): String
    suspend fun createUserAuth(email: String, password: String): String?
    suspend fun createUser(user: User): User
    suspend fun loginUser(email: String, password: String): FirebaseUser?
    suspend fun signOut()
    suspend fun getUser(userId: String): User
    suspend fun getUserLogged(): User?
    suspend fun listUser(): List<User>
}