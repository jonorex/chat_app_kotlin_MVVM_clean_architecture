package com.joao.garageapp.domain.model


import com.google.firebase.firestore.Exclude
import java.util.*


data class ChatMessage (
    val senderId: String = "",
    val receiverId: String = "",
    val messageText: String = "",
    val dateObject: Date = Date(),
    @Exclude
    val dateTime: String = "",
)