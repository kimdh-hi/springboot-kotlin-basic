package com.comments.article.repository

import com.comments.article.domain.Article
import org.springframework.data.repository.CrudRepository

interface ArticleRepository: CrudRepository<Article, Long> {
}