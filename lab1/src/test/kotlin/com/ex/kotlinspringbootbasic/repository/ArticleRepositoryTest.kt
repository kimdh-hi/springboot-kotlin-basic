package com.ex.kotlinspringbootbasic.repository

import com.ex.kotlinspringbootbasic.dto.request.AddArticleDto
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
        val articleDto = AddArticleDto("article4", "content4")

        articleRepository.saveArticle(articleDto)

        assertEquals(4, articleRepository.store.size)
    }

}