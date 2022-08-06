package com.joao.garageapp.ui.chat.conversations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joao.garageapp.databinding.ItemContainerRecentConversionBinding
import com.joao.garageapp.domain.model.Conversation

class ConversationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var bindingRecentConversation: ItemContainerRecentConversionBinding

    private var conversationsList = mutableListOf<Conversation>()
    private var sender: String = ""

    fun setData(list: List<Conversation>, sender: String) {
        conversationsList = list as MutableList<Conversation>
        this.sender = sender
        orderList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        bindingRecentConversation = ItemContainerRecentConversionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ConversationViewHolder(bindingRecentConversation)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ConversationViewHolder).bind(conversationsList[position], sender)
    }

    override fun getItemCount(): Int {
        return conversationsList.size
    }

    private fun orderList() {
        conversationsList.sortWith { obj1, obj2 ->
            obj2.dateObject.compareTo(
                obj1.dateObject
            )
        }
    }


    fun addConversations(list: List<Conversation>) {
        conversationsList.addAll(list)
        orderList()
        notifyDataSetChanged()
    }

    fun addConversation(first: Conversation) {
        conversationsList.add(first)
        orderList()
        notifyItemInserted(0)
    }



    fun tryUpdate(conversation: Conversation): Boolean {

        val conversationPosition = conversationsList.indexOfFirst {
            it.conversationId == conversation.conversationId
        }
        if (conversationPosition != -1) {
            conversationsList[conversationPosition] = conversation
            orderList()
            notifyDataSetChanged()
            return true
        }
        return false
    }

    class ConversationViewHolder(
        private val binding: ItemContainerRecentConversionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation, sender: String) {
            if (sender == conversation.senderId) {
                binding.textRecentMessage.text = "Você: ${conversation.lastMessage}"
                binding.textName.text = conversation.receiverName
                Glide.with(itemView.context).load(conversation.receiverImg).into(binding.imageProfile)
            } else {
                binding.textRecentMessage.text =
                    "${conversation.senderName}: ${conversation.lastMessage}"
                binding.textName.text = conversation.senderName
                Glide.with(itemView.context).load(conversation.senderImg).into(binding.imageProfile)
            }
        }
    }

}