package com.example.cubandroidtest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.databinding.ItemNewsArticleBinding

class NewsArticleAdapter(
    private val onItemClick: (NewsArticle) -> Unit,
) : ListAdapter<NewsArticle, NewsArticleAdapter.NewsArticleViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        val binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NewsArticleViewHolder(private val binding: ItemNewsArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(new: NewsArticle) {
            binding.tvTitle.text = new.title
            binding.tvContent.text = new.content
            new.urlToImage?.let { url ->
                Glide.with(binding.ivIcon.context)
                    .load(url)
                    .placeholder(android.R.drawable.ic_menu_gallery) // Placeholder while loading
                    .error(android.R.drawable.ic_menu_report_image) // Error image if loading fails
                    .into(binding.ivIcon)
            } ?: run {
                // If no image URL, set a default placeholder or hide the ImageView
                binding.ivIcon.setImageResource(android.R.drawable.ic_menu_gallery)
            }
            binding.clParent.setOnClickListener {
                onItemClick(new)
            }
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
        oldItem == newItem
}
