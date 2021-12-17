package com.example.lab2.repository.querydsl.board

import com.example.lab2.domain.Board

interface BoardQueryRepository {

    fun getBoardDetails(boardId: Long): Board?
}