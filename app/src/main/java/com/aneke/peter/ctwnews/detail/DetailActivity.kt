package com.aneke.peter.ctwnews.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.aneke.peter.ctwnews.BuildConfig
import com.aneke.peter.ctwnews.R
import com.aneke.peter.ctwnews.databinding.ActivityDetailBinding
import com.aneke.peter.ctwnews.utils.convertToDate
import com.aneke.peter.ctwnews.utils.parcelable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val detailViewModel : DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel.article.value = intent.parcelable("article")

        title = BuildConfig.FLAVOR.uppercase(Locale.getDefault())
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
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}