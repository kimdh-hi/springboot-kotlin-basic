package com.ex.kotlinspringbootbasic.repository

import com.ex.kotlinspringbootbasic.domain.Article
import com.ex.kotlinspringbootbasic.dto.request.ArticleRequestDto
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException

@Repository
class ArticleRepositoryImpl : ArticleRepository {
    var sequence: Long = 0L
    var store = mutableMapOf<Long, Article>(
        ++sequence to Article("article1", "content1"),
        ++sequence to Article("article2", "content2"),
        ++sequence to Article("article3", "content3"),
    )

    override fun getArticles() = this.store.values

    override fun getArticle(articleId: Long): Article? = this.store.get(articleId)

    override fun saveArticle(articleDto: ArticleRequestDto) {
        val article = Article(articleDto.title, articleDto.content)
        store[++sequence] = article
    }

    override fun deleteArticle(articleId: Long) {
        if (store.containsKey(articleId)) {
            store.remove(articleId)
        } else {
            throw IllegalArgumentException("존재하지 않는 게시글입니다.")
        }
    }

    override fun updateArticle(articleId: Long, articleDto: ArticleRequestDto) {
        if (store.containsKey(articleId)) {
            store[articleId] = Article(articleDto.title, articleDto.content)
        } else {
            throw IllegalArgumentException("존재하지 않는 게시글입니다.")
        }
    }
}