package com.aneke.peter.ctwnews.news

import android.content.Intent
import android.os.Bundle
import com.aneke.peter.ctwnews.BuildConfig
import com.aneke.peter.ctwnews.databinding.ActivityNewsBinding
import com.aneke.peter.ctwnews.detail.DetailActivity
import com.aneke.peter.ctwnews.utils.LoadableActivity
import com.aneke.peter.ctwnews.utils.Status
import com.aneke.peter.ctwnews.utils.convertToTimestamp
import com.aneke.peter.ctwnews.utils.showAlert
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : LoadableActivity() {

    lateinit var binding : ActivityNewsBinding
    lateinit var adapter: NewsAdapter
    val newsViewModel : NewsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)

        newsViewModel.fetchHeadlines()

        setContentView(binding.root)
        title = BuildConfig.FLAVOR.toUpperCase()

        adapter = NewsAdapter { article ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }

        binding.newsList.adapter = adapter

        newsViewModel.headlineResponse.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideLoader()
                        val headlines = resource.data?.articles?.sortedByDescending { it.publishedAt.convertToTimestamp() }
                        adapter.submitList(headlines)
                    }
                    Status.LOADING -> {
                        showLoader()
                    }
                    Status.ERROR -> {
                        hideLoader()
                        showAlert(message = resource.resolveMessage())
                    }
                }
            }
        }

    }
}