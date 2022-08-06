package com.joao.garageapp.ui.chat.conversations

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.domain.model.Conversation
import com.joao.garageapp.domain.model.User
import com.joao.garageapp.domain.usecase.conversations.ListConversationsUseCase
import com.joao.garageapp.domain.usecase.conversations.UpdateConversationUseCase
import com.joao.garageapp.domain.usecase.user.SignOutUseCase
import com.joao.garageapp.util.KEY_RECEIVER_ID
import com.joao.garageapp.util.KEY_SENDER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val updateConversationUseCase: UpdateConversationUseCase,
    private val listConversationsUseCase: ListConversationsUseCase
) : ViewModel() {

    private val _listConversations = MutableLiveData<List<Conversation>?>()
    val listConversations: LiveData<List<Conversation>?> = _listConversations

    fun signOut() =
        viewModelScope.launch {
            try {
                signOutUseCase()
            } catch (e: Exception) {
                Log.d("SignOutUseCase", e.toString())
            }
        }
    fun updateConversation(userS: User, userR: User, chatMessage: ChatMessage) =
        viewModelScope.launch {
            try {
                updateConversationUseCase(userS, userR, chatMessage)
            } catch (e: Exception) {
                Log.d("updateConverseUseCase", e.toString())
            }
        }


    fun listSendConverses(userId: String) = viewModelScope.launch {
        try {
            listConversationsUseCase(KEY_SENDER_ID, userId).collect { conversions ->
                    _listConversations.value = conversions
            }
        } catch (e: Exception) {
            Log.d("ListenConversationsUseCase",e.toString())
        }
    }

    fun listReceivesConverses(userId: String) = viewModelScope.launch {
        try {
            listConversationsUseCase(KEY_RECEIVER_ID, userId).collect { conversions ->
                _listConversations.value = conversions
            }
        } catch (e: Exception) {
            Log.d("ListenConversationsUseCase",e.toString())
        }
    }


}