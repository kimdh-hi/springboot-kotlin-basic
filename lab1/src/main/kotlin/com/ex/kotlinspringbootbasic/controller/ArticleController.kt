package com.ex.kotlinspringbootbasic.controller

import com.ex.kotlinspringbootbasic.dto.request.AddArticleDto
import com.ex.kotlinspringbootbasic.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/articles")
@RestController
class ArticleController(
    private val articleService: ArticleService
) {

    @GetMapping
    fun getArticles() = articleService.getArticles()

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long) = articleService.getArticle(articleId)

    @PostMapping
    fun postArticle(@RequestBody articleDto : AddArticleDto) : ResponseEntity<Any> {
        articleService.saveArticle(articleDto)
        return ResponseEntity(HttpStatus.CREATED)
    }
}