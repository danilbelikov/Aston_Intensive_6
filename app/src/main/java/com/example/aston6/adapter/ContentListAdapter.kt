package com.example.aston6.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.aston6.Constants.BASE_URL
import com.example.aston6.R
import com.example.aston6.databinding.ItemContentBinding
import com.example.aston6.model.ContentModel

class ContentListAdapter(
    private val onClickAction: (ContentModel,positionList: Int ) -> Unit,
    private val context: Context,
    private val onLongClickAction: (position: Int) -> Unit

) : ListAdapter<ContentModel, ContentListAdapter.ContentViewHolder>(
    ContentDiffUtil
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContentBinding.inflate(inflater, parent, false)
        return ContentViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val model = getItem(position)
        holder.bind(model)
        holder.binding.root.setOnClickListener {onClickAction(model,position)}
        holder.binding.root.setOnLongClickListener {
            onLongClickAction(position)
            notifyDataSetChanged()
            return@setOnLongClickListener true
        }
        Glide.with(context).load("${BASE_URL}$position").into(holder.imgContact)
    }
    class ContentViewHolder(val binding: ItemContentBinding) : ViewHolder(binding.root) {
        var imgContact: ImageView = binding.root.findViewById<ImageView>(R.id.imView)
        fun bind(model: ContentModel) {
            binding.tvName.text = model.content
            binding.tvNumber.text = model.number
        }
    }

}