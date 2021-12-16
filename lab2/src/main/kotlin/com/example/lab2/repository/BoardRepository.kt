package com.example.lab2.repository

import com.example.lab2.domain.Board
import com.example.lab2.domain.Member
import com.example.lab2.repository.querydsl.board.BoardQueryRepository
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long>, BoardQueryRepository {

    fun findByMember(member: Member?): List<Board>
}