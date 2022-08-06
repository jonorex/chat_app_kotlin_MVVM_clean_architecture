package com.joao.garageapp.domain.usecase.chat

import com.joao.garageapp.domain.model.ChatMessage

interface SendMessageUseCase {
    suspend operator fun invoke(message: ChatMessage): ChatMessage
}