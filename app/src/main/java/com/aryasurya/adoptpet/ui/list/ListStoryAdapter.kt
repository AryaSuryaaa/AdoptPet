package com.aryasurya.adoptpet.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.TypedArrayUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.databinding.StoryItemBinding
import com.aryasurya.adoptpet.helper.withDateFormat
import com.bumptech.glide.Glide

class ListStoryAdapter : ListAdapter<ListStoryItem, ListStoryAdapter.MyViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallBack: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    class MyViewHolder(private val binding: StoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            Glide.with(itemView.context)
                .load(item.photoUrl)
                .into(binding.ivStory)
            binding.tvNameStory.text = item.name
            binding.tvDescStory.text = item.description
            binding.tvCratedatStory.text = item.createdAt.withDateFormat()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): MyViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder , position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(getItem(holder.adapterPosition))
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}