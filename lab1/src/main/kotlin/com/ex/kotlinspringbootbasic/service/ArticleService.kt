package com.ex.kotlinspringbootbasic.service

import com.ex.kotlinspringbootbasic.repository.ArticleRepositoryImpl
import org.springframework.stereotype.Service

@Service
class ArticleService(private val articleRepository : ArticleRepositoryImpl) {

    /**
     * 전체 Article 조회
     */
    fun getArticles() = articleRepository.getArticles()

    /**
     * Article 단건조회
     */
    fun getArticle(articleId: Long) = articleRepository.getArticle(articleId)
}