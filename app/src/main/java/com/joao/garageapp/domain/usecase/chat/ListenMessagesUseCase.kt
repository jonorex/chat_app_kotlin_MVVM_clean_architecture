package com.joao.garageapp.domain.usecase.chat

import com.joao.garageapp.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ListenMessagesUseCase {
    suspend operator fun invoke(senderId: String, receiverId: String): Flow<List<ChatMessage>?>
}