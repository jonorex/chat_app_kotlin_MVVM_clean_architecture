package com.joao.garageapp.domain.usecase.conversations

import com.joao.garageapp.data.chat.ChatRepository
import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.Conversation
import com.joao.garageapp.domain.model.User
import java.util.*
import javax.inject.Inject

class CreateConversationUseCaseImpl @Inject constructor(
    private val chatRepository: ChatRepository
): CreateConversationUseCase {
    override suspend fun invoke(userS: User, userR: User, message: ChatMessage) {
        val conversation = Conversation(
            lastMessage = message.messageText,
            senderId = userS.userId,
            receiverId = userR.userId,
            senderName = userS.name,
            receiverName = userR.name,
            senderImg = userS.image,
            receiverImg = userR.image,
            dateObject = message.dateObject
        )

        chatRepository.createConversation(conversation)
    }
}