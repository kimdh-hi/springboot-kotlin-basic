package com.example.lab2.repository

import com.example.lab2.domain.Board
import com.example.lab2.domain.Member
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BoardRepositoryTest {

    @Autowired lateinit var boardRepository: BoardRepository
    @Autowired lateinit var userRepository: MemberRepository

    lateinit var member: Member

    @BeforeEach
    fun init() {
        member = Member("member1" , "password1")
        userRepository.save(member)

        val board1: Board = Board("title1", "content1", member)

        val board2: Board = Board("title2", "content2", member)

        boardRepository.saveAll(listOf(board1, board2))
    }

    @Test
    fun `전체 조회 테스트`() {
        val size = boardRepository.findAll().size

        assertEquals(2, size)
    }

    @Test
    fun `Member PK로 조회`() {
        val boards: List<Board> = boardRepository.findByMember(member)

        assertEquals(2, boards.size)
        assertEquals("title1", boards.get(0).title)
    }
}