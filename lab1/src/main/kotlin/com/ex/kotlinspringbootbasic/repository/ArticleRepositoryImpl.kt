package com.ex.kotlinspringbootbasic.repository

import com.ex.kotlinspringbootbasic.domain.Article
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ArticleRepositoryImpl : ArticleRepository {
    var sequence: Long = 0L
    val store = mapOf<Long, Article>(
        ++sequence to Article("article1", "content1"),
        ++sequence to Article("article2", "content2"),
        ++sequence to Article("article3", "content3"),
    )

    override fun getArticles() = this.store.values

    override fun getArticle(articleId: Long): Article? = this.store.get(articleId)
}