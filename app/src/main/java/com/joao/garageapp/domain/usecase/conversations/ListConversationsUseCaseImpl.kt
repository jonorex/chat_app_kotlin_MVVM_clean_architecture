package com.joao.garageapp.domain.usecase.conversations

import com.joao.garageapp.data.chat.ChatRepository
import com.joao.garageapp.domain.model.Conversation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListConversationsUseCaseImpl @Inject constructor(
    private val chatRepository: ChatRepository
): ListConversationsUseCase {

    override suspend fun invoke(type: String, userId: String): Flow<List<Conversation>?> {
        return chatRepository.listConversations(type, userId)
    }

}