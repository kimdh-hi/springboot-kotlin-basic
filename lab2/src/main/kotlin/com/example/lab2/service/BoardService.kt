package com.example.lab2.service

import com.example.lab2.domain.Board
import com.example.lab2.domain.BoardImage
import com.example.lab2.domain.Member
import com.example.lab2.dto.request.BoardUpdateDto
import com.example.lab2.repository.BoardImageRepository
import com.example.lab2.repository.BoardRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardImageRepository: BoardImageRepository,
) {

    val uploadeDir = "C:\\Users\\zbeld\\Documents\\etc\\"

    @Transactional
    fun getBoards() = boardRepository.findAll()

    @Transactional
    fun saveBoard(member: Member, title: String, content: String, file: MultipartFile?) {

        val board = Board(title, content, member)
        boardRepository.save(board)

        val originalFilename: String? = file?.originalFilename
        val saveFileName = getSaveFileName(originalFilename)

        saveFile(file, originalFilename, saveFileName, board)
    }

    @Transactional
    fun saveBoardWithFiles(member: Member, title: String, content: String, files: List<MultipartFile>?) {
        val board = Board(title, content, member)
        boardRepository.save(board)

        if (files?.isNotEmpty() as Boolean) {
            for (file in files) {
                val originalFilename: String? = file?.originalFilename
                val saveFileName = getSaveFileName(originalFilename)

                saveFile(file, originalFilename, saveFileName, board)
            }
        }
    }

    private fun saveFile(
        file: MultipartFile?,
        originalFilename: String?,
        saveFileName: String,
        board: Board
    ) {
        if (file != null && originalFilename != null) {
            val boardImage = BoardImage(originalFilename, saveFileName, board)
            file.transferTo(File(uploadeDir + saveFileName))
            boardImageRepository.save(boardImage)
        }
    }

    private fun getSaveFileName(originalFilename: String?): String {
        val extPosIndex: Int? = originalFilename?.lastIndexOf(".")
        val ext = originalFilename?.substring(extPosIndex?.plus(1) as Int)

        return UUID.randomUUID().toString() + "." + ext
    }


    @Transactional(readOnly = true)
    fun getBoard(id: Long?): Board =
        boardRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 게시글 입니다.")

    @Transactional
    fun updateBoard(id: Long, boardUpdateDto: BoardUpdateDto) {
        val board: Board = boardRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 게시글 입니다.")
        board.updateBoard(boardUpdateDto)
    }

    @Transactional
    fun deleteBoard(id: Long) = boardRepository.deleteById(id)
}
