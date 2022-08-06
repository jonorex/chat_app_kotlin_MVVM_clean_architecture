package com.joao.garageapp.domain.usecase.chat

import com.joao.garageapp.data.chat.ChatRepository
import com.joao.garageapp.domain.model.ChatMessage
import javax.inject.Inject

class SendMessageUseCaseImpl @Inject constructor(
   private val chatRepository: ChatRepository
) :SendMessageUseCase {
    override suspend fun invoke(message: ChatMessage): ChatMessage {
        return chatRepository.sendMessage(message)
    }
}