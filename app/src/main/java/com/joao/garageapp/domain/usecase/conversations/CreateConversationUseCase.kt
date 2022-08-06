package com.joao.garageapp.domain.usecase.conversations

import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.User

interface CreateConversationUseCase {
    suspend operator fun invoke(userS: User, userR: User, message: ChatMessage)
}