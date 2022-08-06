package com.joao.garageapp.data.chat

import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.Conversation
import kotlinx.coroutines.flow.Flow

interface ChatDataSource {
    suspend fun createMessage(chatMessage: ChatMessage): ChatMessage

    suspend fun listenMessagesSend(senderId: String, receiverId: String):  Flow<List<ChatMessage>?>

    suspend fun saveConverse(conversation: Conversation)

    suspend fun updateConversation(conversation: Conversation)

    suspend fun listConversations(type: String,userId: String): Flow<List<Conversation>?>

    suspend fun checkForConversation(senderId: String, receiverId: String): Conversation?


}