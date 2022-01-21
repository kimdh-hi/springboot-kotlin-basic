package com.comments.comment.repository

import com.comments.comment.domain.Comment
import org.springframework.data.repository.CrudRepository

interface CommentRepository: CrudRepository<Comment, Long> {
}