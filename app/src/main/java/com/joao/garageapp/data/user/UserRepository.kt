package com.joao.garageapp.data.user

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.joao.garageapp.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: UserDataSource,
    private val dataStoreSource: DataStoreSource
) {

    suspend fun createUserAuth(email: String, password: String): String? =
        dataSource.createUserAuth(email, password)

    suspend fun createUser(user: User): User = dataSource.createUser(user)

    suspend fun uploadUserImage(imageUri: Uri): String = dataSource.uploadUserImage(imageUri)

    suspend fun loginUser(email: String, password: String): FirebaseUser? = dataSource.loginUser(email, password)

    suspend fun signOut() = dataSource.signOut()

    suspend fun getUser(userId: String) = dataSource.getUser(userId)

    suspend fun getUserLogged() = dataStoreSource.getCurrentUser()

    suspend fun listUser() = dataSource.listUser()

    suspend fun saveUserOnLocalData(user: User): User = dataStoreSource.saveCurrentUser(user)

}