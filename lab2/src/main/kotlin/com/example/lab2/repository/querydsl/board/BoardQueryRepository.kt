package com.example.lab2.repository.querydsl.board

interface BoardQueryRepository {

    fun getBoardDetails(boardId: Long)
}