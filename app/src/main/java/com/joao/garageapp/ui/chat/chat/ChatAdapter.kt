package com.joao.garageapp.ui.chat.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joao.garageapp.databinding.ItemContainerReceivedMessageBinding
import com.joao.garageapp.databinding.ItemContainerSentMessageBinding
import com.joao.garageapp.domain.model.ChatMessage
import com.joao.garageapp.util.VIEW_TYPE_RECEIVE
import com.joao.garageapp.util.VIEW_TYPE_SEND


class ChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var bindingReceivedMessageBinding: ItemContainerReceivedMessageBinding
    private lateinit var bindingSentMessageBinding: ItemContainerSentMessageBinding

    private var messagesList = mutableListOf<ChatMessage>()
    private var senderId: String = ""

    fun setData(messages: List<ChatMessage>, sender: String){
        messagesList = messages as MutableList<ChatMessage>
        senderId = sender
        orderList()
        notifyDataSetChanged()
    }

    fun addMessage(message: ChatMessage, position: Int) {
        messagesList.add(message)
        orderList()
        notifyItemInserted(position)
    }

    private fun orderList(){
        messagesList.sortWith { obj1, obj2 ->
            obj1.dateObject.compareTo(
                obj2.dateObject
            )
        }
    }

    fun addMessages(list: MutableList<ChatMessage>) {
        messagesList.addAll(list)
        orderList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_SEND -> {
                bindingSentMessageBinding = ItemContainerSentMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            SentMessageViewHolder(bindingSentMessageBinding)
            }
            else -> {
                bindingReceivedMessageBinding = ItemContainerReceivedMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ReceiverMessageViewHolder(bindingReceivedMessageBinding)
            }

        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (messagesList[position].senderId == senderId) {
            (holder as SentMessageViewHolder).bind(messagesList[position])
        } else {
            (holder as ReceiverMessageViewHolder).bind(messagesList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messagesList[position].senderId == senderId)
            VIEW_TYPE_SEND
        else
            VIEW_TYPE_RECEIVE
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    class SentMessageViewHolder(
        private val binding: ItemContainerSentMessageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            binding.textMessage.text = chatMessage.messageText
            binding.textDateTime.text = chatMessage.dateTime
        }
    }

    class ReceiverMessageViewHolder(
        private val binding: ItemContainerReceivedMessageBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(chatMessage: ChatMessage) {
            binding.textMessage.text = chatMessage.messageText
            binding.textDateTime.text = chatMessage.dateTime
        }
    }



}