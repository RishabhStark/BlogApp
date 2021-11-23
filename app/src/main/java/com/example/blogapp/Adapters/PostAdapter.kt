package com.example.blogapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.blogapp.Models.Posts
import com.example.blogapp.R
import kotlin.coroutines.coroutineContext

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var title=itemView.findViewById<TextView>(R.id.title)
        var body=itemView.findViewById<TextView>(R.id.body)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Posts>() {
        override fun areItemsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Posts, newItem: Posts): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_item,
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.title.setText(article.title)
        holder.body.setText(article.body)


    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}
