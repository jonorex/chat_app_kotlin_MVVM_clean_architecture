package com.joao.garageapp.data.chat

import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.Conversation
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val dataSource: ChatDataSource
) {
    suspend fun sendMessage(message: ChatMessage): ChatMessage =
        dataSource.createMessage(message)

    suspend fun listenMessagesSend(senderId: String, receiverId: String) =
        dataSource.listenMessagesSend(senderId, receiverId)

    suspend fun createConversation(conversation: Conversation) = dataSource.saveConverse(conversation)

    suspend fun checkForConversation(senderId: String, receiverId: String) = dataSource.checkForConversation(senderId, receiverId)

    suspend fun updateConversation(conversation: Conversation) = dataSource.updateConversation(conversation)

    suspend fun listConversations(type: String, userId: String) = dataSource.listConversations(type, userId)

}