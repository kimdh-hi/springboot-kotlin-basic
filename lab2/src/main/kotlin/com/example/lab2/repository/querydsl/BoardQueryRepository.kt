package com.example.lab2.repository.querydsl

interface BoardQueryRepository {

    fun getBoardDetails(boardId: Long)
}