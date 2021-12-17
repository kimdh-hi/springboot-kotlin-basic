package com.example.lab2.service

import com.example.lab2.domain.Board
import com.example.lab2.domain.Member
import com.example.lab2.dto.request.board.BoardUpdateDto
import com.example.lab2.repository.BoardRepository
import com.example.lab2.repository.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class BoardServiceTest {

    @Autowired lateinit var userRepository: MemberRepository
    @Autowired lateinit var boardRepository: BoardRepository
    @Autowired lateinit var  boardService: BoardService
    lateinit var member: Member
    lateinit var board: Board

    @BeforeEach
    fun init() {
        member = Member("member1" , "password1")
        userRepository.save(member)

        board = Board("title0", "content0", member)
        boardRepository.save(board)
    }


    @Test
    @DisplayName("Board 단건조회")
    fun `board 단건조회`() {
        val findBoard = boardService.getBoard(board.id as Long)

        assertNotNull(findBoard)
        assertEquals("title0", findBoard.title)
    }

    @Test
    @DisplayName("Board 수정")
    fun `board 수정`() {
        boardService.updateBoard(board.id as Long, BoardUpdateDto("updateTitle", "updateContent"))

        val findBoard = boardRepository.findById(member.id as Long).get()

        assertEquals("updateTitle", findBoard.title)
        assertEquals("updateContent", findBoard.content)
    }

    @Test
    @DisplayName("Board 삭제")
    fun `board 삭제`() {
        boardService.deleteBoard(board.id as Long)

        assertNull(boardRepository.findByIdOrNull(board.id as Long))
    }

}