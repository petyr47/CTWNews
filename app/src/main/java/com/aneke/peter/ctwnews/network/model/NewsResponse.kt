package com.aneke.peter.ctwnews.network.model

data class NewsResponse(
    val articles: List<Article> = listOf(),
    val status: String = "",
    val totalResults: Int = 0,
    val message : String = "",
    val success : Boolean = true
)