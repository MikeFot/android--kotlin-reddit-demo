package com.michaelfotiadis.demo.reddit.android.ui.activity.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.michaelfotiadis.demo.reddit.android.databinding.ItemPostBinding
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.UiPost
import io.noties.markwon.Markwon
import timber.log.Timber


class PostsRecyclerViewAdapter(
    private val callback: Callback,
    private val markwon: Markwon
) : PagedListAdapter<UiPost, PostsRecyclerViewAdapter.ViewHolder>(DiffCallback()) {

    interface Callback {
        fun onPostClicked(uiPost: UiPost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->

            with(holder.itemView) {
                setOnClickListener { callback.onPostClicked(item) }
            }

            markwon.setMarkdown(holder.titleView, item.title)
            markwon.setMarkdown(holder.contentView, item.previewContent)
            holder.timeAgoView.text = item.timeAgoMessage

            with(holder.thumbnailImageView) {
                item.thumbnailLink?.let { thumbnailLink ->
                    Glide.with(this)
                        .load(thumbnailLink)
                        .centerInside()
                        .into(this)
                }
            }
        }
    }

    class ViewHolder(binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        val cardView: CardView = binding.itemCard
        val titleView: TextView = binding.title
        val contentView: TextView = binding.content
        val timeAgoView: TextView = binding.timeAgoText
        val thumbnailImageView: ImageView = binding.thumbnailImage
    }

    class DiffCallback : DiffUtil.ItemCallback<UiPost>() {
        override fun areItemsTheSame(oldItem: UiPost, newItem: UiPost): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UiPost, newItem: UiPost): Boolean {
            // avoid excessive refreshes due to votes and awards changing
            return oldItem.title == newItem.title
                    && oldItem.previewContent == newItem.previewContent
                    && oldItem.permalink == newItem.permalink
        }
    }

}