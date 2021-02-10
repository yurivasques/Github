package com.yurivasques.github.myapplication.scenes.repotaglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.myapplication.R
import com.yurivasques.github.myapplication.scenes.repolist.ReposAdapter
import io.reactivex.rxjava3.subjects.PublishSubject

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