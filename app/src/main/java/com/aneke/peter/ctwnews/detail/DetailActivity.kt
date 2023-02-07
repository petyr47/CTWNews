package com.aneke.peter.ctwnews.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.aneke.peter.ctwnews.BuildConfig
import com.aneke.peter.ctwnews.R
import com.aneke.peter.ctwnews.databinding.ActivityDetailBinding
import com.aneke.peter.ctwnews.utils.convertToDate
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailBinding
    val detailViewModel : DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.article.value = intent.getParcelableExtra("article")

        title = BuildConfig.FLAVOR.toUpperCase()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel.article.observe(this) {
            it?.let { article ->
                binding.itemTitle.text = article.title
                binding.itemDate.text = article.publishedAt.convertToDate()
                binding.itemDescription.text = article.description
                binding.itemContent.text = article.content

                binding.articleImage.load(article.urlToImage) {
                    crossfade(true)
                    placeholder(R.drawable.ic_times_logo)
                    error(R.drawable.ic_times_logo)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}