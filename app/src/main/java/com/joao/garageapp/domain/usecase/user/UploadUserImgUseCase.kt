package com.joao.garageapp.domain.usecase.user

import android.net.Uri

interface UploadUserImgUseCase {
    suspend operator fun invoke(imageUri: Uri): String
}