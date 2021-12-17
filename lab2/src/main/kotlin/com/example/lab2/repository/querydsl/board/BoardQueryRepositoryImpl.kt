package com.example.lab2.repository.querydsl.board

import com.example.lab2.domain.Board
import com.example.lab2.domain.QBoard.board
import com.example.lab2.domain.QBoardImage
import com.example.lab2.domain.QBoardImage.boardImage
import com.example.lab2.dto.response.board.BoardDetailsDto
import com.example.lab2.dto.response.board.QBoardDetailsDto
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BoardQueryRepositoryImpl(private val query: JPAQueryFactory) : BoardQueryRepository {

    override fun getBoardDetails(boardId: Long): Board? {
        return query
            .select(board)
            .from(board)
            .leftJoin(board.boardImages, boardImage).fetchJoin()
            .where(board.id.eq(boardId))
            .fetchFirst()
    }
}