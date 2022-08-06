package com.joao.garageapp.ui.chat.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao.garageapp.data.chat.ChatRepository
import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.usecase.chat.ListenMessagesUseCase
import com.joao.garageapp.domain.usecase.chat.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val listenMessagesUseCase: ListenMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : ViewModel() {
    private val _listMessages = MutableLiveData<List<ChatMessage>?>()
    val listMessages: LiveData<List<ChatMessage>?> = _listMessages

    private val _listMessages2 = MutableLiveData<List<ChatMessage>?>()
    val listMessages2: LiveData<List<ChatMessage>?> = _listMessages2


    private val _messageSend = MutableLiveData<ChatMessage>()
    val messageSend: LiveData<ChatMessage> = _messageSend


    fun listMessages(senderId: String, receiverId: String) = viewModelScope.launch {
        try {
            listenMessagesUseCase(senderId, receiverId).collect { messagesSend ->
                if(messagesSend != null)
                    _listMessages.value = messagesSend
            }
        } catch (e: Exception) {
            Log.d("ListenMessagesUseCase",e.toString())
        }
    }

    fun listMessages2(senderId: String, receiverId: String) = viewModelScope.launch {
        try {
            listenMessagesUseCase(receiverId, senderId).collect { messagesSend ->
                    _listMessages.value = messagesSend
            }
        } catch (e: Exception) {
            Log.d("ListenMessagesUseCase",e.toString())
        }
    }


    fun sendMessage(senderId: String, receiverId: String, messageText: String) = viewModelScope.launch {
        val message = ChatMessage(senderId, receiverId, messageText, Date())
        try {
            val m = sendMessageUseCase(message)
            _messageSend.value = m

        } catch (e:Exception) {
            Log.d("CreateMessageUseCase", e.toString())
        }
    }
}