package com.joao.garageapp.domain.usecase.conversations

import com.joao.garageapp.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

interface ListConversationsUseCase {
    suspend operator fun invoke(type: String, userId: String): Flow<List<Conversation>?>
}