package com.example.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.data.model.Article
import com.example.newsapp.databinding.NewsListItemBinding

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(
            oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context),
                parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article, position+1)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(private val itemBinding: NewsListItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(article: Article, position: Int){
            itemBinding.newsTitle.text = article.title
            itemBinding.newsDescription.text = article.description
            itemBinding.newsSource.text = article.source?.name
            itemBinding.newsNumber.text = "$position."
            if(position == differ.currentList.size)
                itemBinding.divider.visibility = View.INVISIBLE
            Glide.with(itemBinding.newsImageView.context)
                .load(article.urlToImage)
                .into(itemBinding.newsImageView)

            itemBinding.root.setOnClickListener{
                onItemClick?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClick :((Article) -> Unit)?=null

    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClick = listener
    }
}