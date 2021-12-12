package com.ex.kotlinspringbootbasic.repository

import com.ex.kotlinspringbootbasic.domain.Article

interface ArticleRepository {

    fun getArticles() : Collection<Article>

    fun getArticle(articleId: Long) : Article?
}