package com.joao.garageapp.domain.usecase.user

import android.net.Uri
import com.joao.garageapp.data.user.UserRepository
import javax.inject.Inject

class UploadUserImgUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): UploadUserImgUseCase {

    override suspend fun invoke(imageUri: Uri): String {
        return userRepository.uploadUserImage(imageUri)
    }
}