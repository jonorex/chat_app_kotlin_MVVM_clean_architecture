package com.joao.garageapp.domain.usecase.user

import com.joao.garageapp.data.user.DataSourceImpl
import com.joao.garageapp.data.user.UserRepository
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val dataSourceImpl: DataSourceImpl
): SignOutUseCase {

    override suspend fun invoke() {
        userRepository.signOut()
        dataSourceImpl.clearDataStore()
    }

}