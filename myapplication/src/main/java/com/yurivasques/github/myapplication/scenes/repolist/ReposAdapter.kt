package com.yurivasques.github.myapplication.scenes.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.myapplication.R
import io.reactivex.rxjava3.subjects.PublishSubject

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    val repoClickIntent: PublishSubject<Repo> = PublishSubject.create()

    var data: MutableList<Repo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(position: Int, repo: Repo) {
        data[position] = repo
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], repoClickIntent)

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            repo: Repo,
            repoClickIntent: PublishSubject<Repo>
        ) = with(itemView) {

            val textRepoName = itemView.findViewById<TextView>(R.id.textRepoName)
            textRepoName.text = repo.name
            val textRepoDescription = itemView.findViewById<TextView>(R.id.textRepoDescription)
            textRepoDescription.text = repo.description

            setOnClickListener { repoClickIntent.onNext(repo) }
        }
    }
}