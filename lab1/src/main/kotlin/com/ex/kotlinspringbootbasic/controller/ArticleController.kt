package com.ex.kotlinspringbootbasic.controller

import com.ex.kotlinspringbootbasic.service.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/articles")
@RestController
class ArticleController(
    private val articleService: ArticleService
) {

    @GetMapping
    fun getArticles() = articleService.getArticles()

    @GetMapping("/{articleId}")
    fun getArticle(@PathVariable articleId: Long) = articleService.getArticle(articleId)

}