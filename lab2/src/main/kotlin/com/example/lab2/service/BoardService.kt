package com.example.lab2.service

import com.example.lab2.domain.Board
import com.example.lab2.domain.Member
import com.example.lab2.dto.request.BoardDto
import com.example.lab2.dto.request.BoardUpdateDto
import com.example.lab2.repository.BoardRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService (
    private val boardRepository: BoardRepository
    ) {

    @Transactional
    fun saveBoard(member: Member, boardDto: BoardDto) = boardRepository.save(Board(boardDto.title, boardDto.content, member))

    @Transactional(readOnly = true)
    fun getBoard(id: Long?): Board = boardRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 게시글 입니다.")

    @Transactional
    fun updateBoard(id: Long, boardUpdateDto: BoardUpdateDto) {
        val board: Board = boardRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 게시글 입니다.")
        board.updateBoard(boardUpdateDto)
    }

    @Transactional
    fun deleteBoard(id: Long) = boardRepository.deleteById(id)


}
