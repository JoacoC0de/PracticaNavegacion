package com.example.practicanavegacion.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicanavegacion.R
import com.example.practicanavegacion.model.Message

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {
    private var messages: List<Message> = emptyList()

    fun submitList(list: List<Message>) {
        messages = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount() = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvContent: TextView = itemView.findViewById(R.id.tvContent)
        fun bind(message: Message) {
            tvContent.text = message.message
        }
    }
}