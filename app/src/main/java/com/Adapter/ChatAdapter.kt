package com.example.practicanavegacion.ui.chats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicanavegacion.databinding.ItemChatBinding
import com.example.practicanavegacion.model.ChatResponse

class ChatAdapter(
    private var chats: List<ChatResponse>
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatResponse) {
            binding.tvWorkerName.text = chat.workerName
            binding.tvLastMessage.text = chat.lastMessage

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chats[position])
    }

    override fun getItemCount() = chats.size

    fun updateList(newList: List<ChatResponse>) {
        chats = newList
        notifyDataSetChanged()
    }
}