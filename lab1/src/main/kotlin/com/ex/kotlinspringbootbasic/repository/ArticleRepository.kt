package com.ex.kotlinspringbootbasic.repository

import com.ex.kotlinspringbootbasic.domain.Article
import com.ex.kotlinspringbootbasic.dto.request.AddArticleDto

interface ArticleRepository {

    fun getArticles() : Collection<Article>

    fun getArticle(articleId: Long) : Article?

    fun saveArticle(articleDto: AddArticleDto)
}