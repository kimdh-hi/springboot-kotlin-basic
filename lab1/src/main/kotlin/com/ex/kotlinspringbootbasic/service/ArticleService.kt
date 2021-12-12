package com.ex.kotlinspringbootbasic.service

import com.ex.kotlinspringbootbasic.dto.request.ArticleRequestDto
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

    /**
     * Article 추가
     */
    fun saveArticle(articleDto: ArticleRequestDto) = articleRepository.saveArticle(articleDto)


    /**
     * Article 삭제
     */
    fun deleteArticle(articleId: Long) = articleRepository.deleteArticle(articleId)

    /**
     * Article 수정
     */
    fun updateArticle(articleId: Long, articleDto: ArticleRequestDto) = articleRepository.updateArticle(articleId, articleDto)
}