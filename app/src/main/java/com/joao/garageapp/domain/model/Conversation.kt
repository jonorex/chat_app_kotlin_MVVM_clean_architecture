package com.joao.garageapp.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Conversation (
    var conversationId: String = "",
    var lastMessage: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val senderName: String = "",
    val receiverName: String = "",
    val senderImg: String = "",
    val receiverImg: String = "",
    var dateObject: Date = Date(),
    @Exclude
    val dateStr: String = ""
        ): Parcelable