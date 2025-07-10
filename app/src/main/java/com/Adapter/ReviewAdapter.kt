package com.example.practicanavegacion.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicanavegacion.databinding.ItemReviewBinding
import com.example.practicanavegacion.model.Review

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private var reviews: List<Review> = listOf()

    inner class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.tvClientName.text = review.client_name
            binding.tvComment.text = review.comment
            binding.tvRating.text = "Puntaje: ${review.rating}"
            binding.tvDate.text = review.created_at
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount() = reviews.size

    fun updateList(newList: List<Review>) {
        reviews = newList
        notifyDataSetChanged()
    }
}