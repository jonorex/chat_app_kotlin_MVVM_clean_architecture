package com.joao.garageapp.data.chat

import android.util.Log
import com.google.firebase.firestore.*
import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.Conversation
import com.joao.garageapp.util.COLLECTION_CONVERSATIONS
import com.joao.garageapp.util.COLLECTION_MESSAGES
import com.joao.garageapp.util.KEY_RECEIVER_ID
import com.joao.garageapp.util.KEY_SENDER_ID
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


class FirebaseChatDataSource @Inject constructor(
    firebaseFirestore: FirebaseFirestore
) : ChatDataSource {

    private val chatMessagesFirestore = firebaseFirestore.collection(COLLECTION_MESSAGES)

    private val conversationsFirestore = firebaseFirestore.collection(COLLECTION_CONVERSATIONS)


    override suspend fun createMessage(chatMessage: ChatMessage): ChatMessage {
        return suspendCoroutine { continuation ->
            chatMessagesFirestore.add(chatMessage)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(chatMessage))
                }
                .addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }

    }


    override suspend fun listenMessagesSend(
        senderId: String,
        receiverId: String
    ): Flow<List<ChatMessage>?> =
        callbackFlow {
            val messages = chatMessagesFirestore
                .whereEqualTo(KEY_SENDER_ID, senderId)
                .whereEqualTo(KEY_RECEIVER_ID, receiverId)
                .addSnapshotListener { snapshot, _ ->
                    if (snapshot == null) {
                        return@addSnapshotListener
                    }
                    try {
                        val messagesList = mutableListOf<ChatMessage>()
                        for (documentChange in snapshot.documentChanges) {
                            if (documentChange.type == DocumentChange.Type.ADDED) {
                                documentChange.document.toObject(ChatMessage::class.java).run {
                                    messagesList.add(this)
                                }
                            }

                        }
                        trySend(messagesList)
                    } catch (e: Throwable) {
                        Log.d("ChatRepository", e.toString())
                    }
                }

            awaitClose { messages.remove() }
        }


    override suspend fun saveConverse(conversation: Conversation) {
        conversationsFirestore.add(conversation)
    }

    override suspend fun listConversations(type: String, userId: String): Flow<List<Conversation>?> {
        return callbackFlow {
            val conversations = conversationsFirestore
                .whereEqualTo(type, userId)
                .addSnapshotListener { snapshot, _ ->
                    if(snapshot == null) {
                        return@addSnapshotListener
                    }
                    try {
                        val conversations = mutableListOf<Conversation>()
                        for (documentChange in snapshot.documentChanges) {
                            if(documentChange.type == DocumentChange.Type.ADDED) {
                                documentChange.document.toObject(Conversation::class.java).run {
                                    this.conversationId = documentChange.document.id
                                    conversations.add(this)
                                }
                            } else if(documentChange.type == DocumentChange.Type.MODIFIED) {
                                documentChange.document.toObject(Conversation::class.java).run {
                                    this.conversationId = documentChange.document.id
                                    conversations.add(this)
                                }
                            }
                        }
                        Log.d("ChatRepositoryConversesList", "callback")
                        trySend(conversations)
                    } catch (e: Throwable) {
                        Log.d("ChatRepositoryConversesList", e.toString())
                    }
                }
            awaitClose {conversations.remove()}
        }
    }

    override suspend fun checkForConversation(senderId: String, receiverId: String): Conversation? {
        return suspendCoroutine { continuation ->
            conversationsFirestore
                .whereEqualTo(KEY_SENDER_ID, senderId)
                .whereEqualTo(KEY_RECEIVER_ID, receiverId)
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.size() > 0) {
                        val document = documents.first()
                        var conversation: Conversation
                        document.toObject(Conversation::class.java).run {
                            this.conversationId = document.id
                            conversation = this

                        }
                        continuation.resumeWith(Result.success(conversation))
                    } else {
                        continuation.resumeWith(Result.success(null))
                    }
                }.addOnFailureListener {
                    continuation.resumeWith(Result.failure(it))
                }
        }
    }

    override suspend fun updateConversation(conversation: Conversation) {
        conversationsFirestore.document(conversation.conversationId).set(conversation)
    }


}