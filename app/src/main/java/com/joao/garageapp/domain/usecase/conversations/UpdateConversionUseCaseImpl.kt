package com.joao.garageapp.domain.usecase.conversations

import com.joao.garageapp.data.chat.ChatRepository
import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.Conversation
import com.joao.garageapp.domain.model.User
import javax.inject.Inject

class UpdateConversionUseCaseImpl @Inject constructor(
    private val createConversationUseCase: CreateConversationUseCase,
    private val chatRepository: ChatRepository
): UpdateConversationUseCase {
    override suspend fun invoke(userS: User, userR: User, chatMessage: ChatMessage) {
        val conversationA = chatRepository.checkForConversation(userS.userId, userR.userId)
        val conversationB = chatRepository.checkForConversation(userR.userId, userS.userId)

        when {
            conversationA != null -> {
                val conversation = Conversation(
                    conversationId = conversationA.conversationId,
                    dateObject = chatMessage.dateObject,
                    lastMessage = chatMessage.messageText,
                    senderId = userS.userId,
                    senderImg = userS.image,
                    senderName = userS.name,
                    receiverId = userR.userId,
                    receiverName = userR.name,
                    receiverImg = userR.image,
                )
                chatRepository.updateConversation(conversation)
            }
            conversationB != null -> {
                val conversation = Conversation(
                    conversationId = conversationB.conversationId,
                    dateObject = chatMessage.dateObject,
                    lastMessage = chatMessage.messageText,
                    senderId = userS.userId,
                    senderImg = userS.image,
                    senderName = userS.name,
                    receiverId = userR.userId,
                    receiverName = userR.name,
                    receiverImg = userR.image,
                )
                chatRepository.updateConversation(conversation)
            }
            else -> {
                createConversationUseCase(userS, userR, chatMessage)
            }
        }
    }
}