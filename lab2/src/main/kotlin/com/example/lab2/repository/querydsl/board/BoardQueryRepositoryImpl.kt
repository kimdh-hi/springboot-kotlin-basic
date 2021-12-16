package com.example.lab2.repository.querydsl.board

import com.example.lab2.domain.QBoard.board
import com.example.lab2.domain.QBoardImage.boardImage
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BoardQueryRepositoryImpl(private val query: JPAQueryFactory) : BoardQueryRepository {

    override fun getBoardDetails(boardId: Long) {
        query.select(board)
            .from(board)
            .join(boardImage.board, board)
            .where(board.id.eq(boardId))
    }
}