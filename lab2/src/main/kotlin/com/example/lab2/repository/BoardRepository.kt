package com.example.lab2.repository

import com.example.lab2.domain.Board
import com.example.lab2.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {

    fun findByMember(member: Member?): List<Board>
}