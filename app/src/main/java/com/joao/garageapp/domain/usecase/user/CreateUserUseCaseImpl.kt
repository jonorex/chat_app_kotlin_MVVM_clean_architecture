package com.joao.garageapp.domain.usecase.user

import android.net.Uri
import com.joao.garageapp.data.user.UserRepository
import com.joao.garageapp.domain.model.User
import javax.inject.Inject

class CreateUserUseCaseImpl @Inject constructor(
    private val uploadUserImgUseCase: UploadUserImgUseCase,
    private val createUserAuth: CreateUserAuth,
    private val userRepository: UserRepository,
): CreateUserUseCase {

    override suspend fun invoke(name: String, image: Uri, email: String, password: String): User {
        return try {
            val userId = createUserAuth(email, password)
            val url = uploadUserImgUseCase(image)
            val user = User(userId!!, name, url, email, "")
            userRepository.createUser(user)
            userRepository.saveUserOnLocalData(user)
        } catch (e: Exception) {
            throw e
        }
    }
}