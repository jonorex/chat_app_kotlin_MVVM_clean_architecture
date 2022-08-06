package com.joao.garageapp.domain.usecase.chat

import com.joao.garageapp.data.chat.ChatRepository
import com.joao.garageapp.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenMessagesUseCaseImpl @Inject constructor (
    private val chatRepository: ChatRepository
        ): ListenMessagesUseCase {
    override suspend fun invoke(senderId: String, receiverId: String): Flow<List<ChatMessage>?> {
        return chatRepository.listenMessagesSend(senderId, receiverId)
    }
}