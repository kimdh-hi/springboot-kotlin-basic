package com.ex.kotlinspringbootbasic.controller

import com.ex.kotlinspringbootbasic.dto.request.ArticleRequestDto
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
    fun postArticle(@RequestBody articleDto : ArticleRequestDto) : ResponseEntity<Any> {
        articleService.saveArticle(articleDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping("/{articleId}")
    fun deleteArticle(@PathVariable articleId: Long) = articleService.deleteArticle(articleId)

    @PutMapping("/{articleId}")
    fun updateArticle(
        @PathVariable articleId: Long,
        @RequestBody articleDto: ArticleRequestDto
    ) = articleService.updateArticle(articleId, articleDto)
}