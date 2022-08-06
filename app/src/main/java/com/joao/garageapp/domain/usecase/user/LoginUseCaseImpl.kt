package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.data.user.DataStoreSource
import com.joao.garageapp.data.user.UserRepository
import com.joao.garageapp.domain.model.User
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val getUser: GetUserUseCase,
    private val dataStoreSource: DataStoreSource
): LoginUseCase {
    override suspend fun invoke(email: String, password: String): User? {
        return try {
            val user = userRepository.loginUser(email, password)
            val id: String = user!!.uid
            val u = getUser(id)
            dataStoreSource.saveCurrentUser(u)
        } catch (e: Exception) {
            throw e
        }

    }
}