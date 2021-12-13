package com.ex.kotlinspringbootbasic.repository

import com.ex.kotlinspringbootbasic.domain.Article
import com.ex.kotlinspringbootbasic.dto.request.ArticleRequestDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ArticleRepositoryTest {

    private val articleRepository = ArticleRepositoryImpl()

    @DisplayName("1. Article 전체조회")
    @Test
    fun getArticles() {
        val articles = articleRepository.getArticles()

        assertEquals(3, articles.size)
    }

    @DisplayName("2. Article 단건조회")
    @Test
    fun getArticle() {
        val article = articleRepository.getArticle(1L)

        assertEquals("article1", article?.title)
    }

    @DisplayName("3. Article 단건조회 (존재하지 않는 id)")
    @Test
    fun failedGetArticle() {
        val article = articleRepository.getArticle(4L)

        assertEquals(null, article?.title)
    }

    @Test
    @DisplayName("4. Article 추가")
    fun addArticle() {
        val articleDto = ArticleRequestDto("article4", "content4")

        articleRepository.saveArticle(articleDto)

        assertEquals(4, articleRepository.store.size)
    }

    @Test
    @DisplayName("5. Article 삭제")
    fun deleteArticle() {
        // Given
        val deleteArticleId = 1L

        // When
        articleRepository.deleteArticle(deleteArticleId)

        // Then
        assertEquals(2, articleRepository.store.size)
    }

    @Test
    @DisplayName("6. Article 수정")
    fun updateArticle() {
        // Given
        val updateArticleId = 1L
        val updateArticleDto = ArticleRequestDto("updatedTitle", "updatedContent")

        // When
        articleRepository.updateArticle(updateArticleId, updateArticleDto)

        // Then
        assertEquals(3, articleRepository.store.size)
        assertEquals("updatedTitle", articleRepository.store[updateArticleId]?.title)
    }
}