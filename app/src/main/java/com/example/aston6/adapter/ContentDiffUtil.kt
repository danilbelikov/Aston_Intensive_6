package com.example.aston6.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.aston6.model.ContentModel

object ContentDiffUtil : DiffUtil.ItemCallback<ContentModel>() {
    override fun areItemsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContentModel, newItem: ContentModel): Boolean {
         return oldItem == newItem
    }

}