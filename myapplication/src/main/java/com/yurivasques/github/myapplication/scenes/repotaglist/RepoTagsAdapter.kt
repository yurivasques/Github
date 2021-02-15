package com.yurivasques.github.myapplication.scenes.repoTagList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.yurivasques.github_library.domain.model.Tag
import com.yurivasques.github.myapplication.R

class RepoTagsAdapter : RecyclerView.Adapter<RepoTagsAdapter.ViewHolder>() {

    var data: MutableList<Tag> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(position: Int, tag: Tag) {
        data[position] = tag
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
        )

    override fun onBindViewHolder(holder: RepoTagsAdapter.ViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            tag: Tag
        ) = with(itemView) {
            val textTagName = itemView.findViewById<TextView>(R.id.textTagName)
            textTagName.text = tag.name
        }
    }

}