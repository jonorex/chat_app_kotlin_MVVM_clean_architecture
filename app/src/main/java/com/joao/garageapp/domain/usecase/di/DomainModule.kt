package com.joao.garageapp.domain.usecase.di

import com.joao.garageapp.domain.usecase.chat.*
import com.joao.garageapp.domain.usecase.conversations.*
import com.joao.garageapp.domain.usecase.user.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindCrateUserUseCase(useCase: CreateUserUseCaseImpl): CreateUserUseCase

    @Binds
    fun bindUploadUserImageUseCase(useCase: UploadUserImgUseCaseImpl): UploadUserImgUseCase

    @Binds
    fun bindCreateUserAUth(useCase: CreateUserAuthImpl): CreateUserAuth

    @Binds
    fun bindGetUserUseCase(useCase: GetUserUseCaseImpl): GetUserUseCase

    @Binds
    fun bindLoginUseCase(useCase: LoginUseCaseImpl): LoginUseCase

    @Binds
    fun bindSignOut(useCase: SignOutUseCaseImpl): SignOutUseCase

    @Binds
    fun bindGetUserLogged(useCase: GetUserLoggedUseCaseImpl): GetUserLoggedUseCase

    @Binds
    fun binListUser(useCase: ListUserUseCaseImpl): ListUsersUseCase

    @Binds
    fun bindListenMessages(useCase: ListenMessagesUseCaseImpl): ListenMessagesUseCase

    @Binds
    fun bindSendMessage(useCase: SendMessageUseCaseImpl): SendMessageUseCase

    @Binds
    fun bindCreateConversation(useCase: CreateConversationUseCaseImpl): CreateConversationUseCase

    @Binds
    fun bindUpdateConversation(useCase: UpdateConversionUseCaseImpl): UpdateConversationUseCase

    @Binds
    fun bindListConversations(useCase: ListConversationsUseCaseImpl): ListConversationsUseCase

}