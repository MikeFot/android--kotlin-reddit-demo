package com.michaelfotiadis.demo.reddit.android.ui.activity.main.fragment.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.michaelfotiadis.demo.reddit.android.databinding.ItemPostBinding
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.fragment.posts.placeholder.PlaceholderContent.PlaceholderItem

class PostsRecyclerViewAdapter(

) : RecyclerView.Adapter<PostsRecyclerViewAdapter.ViewHolder>() {

    private val values = mutableListOf<PlaceholderItem>()

    fun setItems(items: List<PlaceholderItem>) {
        values.run {
            clear()
            addAll(items)
        }
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
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}