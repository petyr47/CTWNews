package com.aneke.peter.ctwnews.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.aneke.peter.ctwnews.R
import com.aneke.peter.ctwnews.databinding.ItemNewsBinding
import com.aneke.peter.ctwnews.network.model.Article
import com.aneke.peter.ctwnews.utils.convertToDate

class NewsAdapter(val onItemClickListener : (Article) -> Unit) : ListAdapter<Article, NewsAdapter.NewsHolder>(ArticleDiffCallback())   {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) =
        holder.bindArticle(getItem(position))

    inner class NewsHolder(private val binding : ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindArticle(article : Article) {
            binding.itemTitle.text = article.title
            binding.itemDate.text = article.publishedAt.convertToDate()

            binding.itemImage.load(article.urlToImage) {
                crossfade(true)
                placeholder(R.drawable.ic_times_logo)
                error(R.drawable.ic_times_logo)
                transformations(RoundedCornersTransformation(2f, 2f, 2f, 2f))
            }
            binding.root.setOnClickListener { onItemClickListener(article) }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title.contentEquals(newItem.title)
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

}