package com.ex.kotlinspringbootbasic.repository

import com.ex.kotlinspringbootbasic.domain.Article
import com.ex.kotlinspringbootbasic.dto.request.ArticleRequestDto

interface ArticleRepository {

    fun getArticles() : Collection<Article>

    fun getArticle(articleId: Long) : Article?

    fun saveArticle(articleDto: ArticleRequestDto)

    fun deleteArticle(articleId: Long)

    fun updateArticle(articleId: Long, articleDto: ArticleRequestDto)
}