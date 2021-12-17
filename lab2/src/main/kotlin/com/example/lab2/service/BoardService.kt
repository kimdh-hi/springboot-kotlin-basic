package com.example.lab2.service

import com.example.lab2.domain.Board
import com.example.lab2.domain.BoardImage
import com.example.lab2.domain.Member
import com.example.lab2.dto.request.board.BoardUpdateDto
import com.example.lab2.dto.response.board.BoardDetailsDto
import com.example.lab2.repository.BoardImageRepository
import com.example.lab2.repository.BoardRepository
import com.example.lab2.utils.S3Uploader
import com.example.lab2.utils.S3Utils
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
    private val s3Utils: S3Utils,
    private val s3UploaderV2: S3Utils,
) {

    val uploadeDir = "C:\\Users\\zbeld\\Documents\\etc\\"

    @Transactional
    fun getBoards() = boardRepository.findAll()

    @Transactional
    fun saveBoard(member: Member, title: String, content: String, file: MultipartFile?) {

        val board = Board(title, content, member)
        boardRepository.save(board)

        file?.let {
            val originalFilename = file.originalFilename ?: UUID.randomUUID().toString()
            val saveFileName = getSaveFileName(originalFilename)

            println(originalFilename)
            println(saveFileName)

            saveFile(file, originalFilename, saveFileName, board)
        }
    }

    @Transactional
    fun saveBoardWithFiles(member: Member, title: String, content: String, files: List<MultipartFile>?) {
        val board = Board(title, content, member)
        boardRepository.save(board)

        files?.let{
            for (file in files) {
                val originalFilename = file.originalFilename ?: UUID.randomUUID().toString()
                val saveFileName = getSaveFileName(originalFilename)

                saveFile(file, originalFilename, saveFileName, board)
            }
        }
    }

    @Transactional
    fun saveBoardV2(member: Member, title: String, content: String, files: List<MultipartFile>?) {
        val board = Board(title, content, member)
        val savedBoard = boardRepository.save(board)

        files?.let {
            for (file in files) {
                val s3UploadResponseDto = s3Utils.upload(file, "static")
                val boardImage = BoardImage(s3UploadResponseDto.originalFileName, s3UploadResponseDto.saveFileName)
                savedBoard.addBoardImage(boardImage)
            }
        }
    }

    @Transactional(readOnly = true)
    fun getBoard(id: Long): BoardDetailsDto {
        val board: Board = boardRepository.getBoardDetails(id)
            ?: throw IllegalArgumentException("존재하지 않는 게시글 입니다.")

        val boardDetailsDto = BoardDetailsDto(board.title, board.content, null)

        val fileNames = board.boardImages.map {
            it.saveFileName
        }
        boardDetailsDto.fileNames = fileNames

        return boardDetailsDto
    }




    @Transactional
    fun updateBoard(id: Long, boardUpdateDto: BoardUpdateDto) {
        val board: Board = boardRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 게시글 입니다.")
        board.updateBoard(boardUpdateDto)
    }

    @Transactional
    fun deleteBoard(id: Long) = boardRepository.deleteById(id)


    private fun getSaveFileName(originalFilename: String?): String {
        val extPosIndex: Int? = originalFilename?.lastIndexOf(".")
        val ext = originalFilename?.substring(extPosIndex?.plus(1) as Int)

        return UUID.randomUUID().toString() + "." + ext
    }

    private fun saveFile(
        file: MultipartFile,
        originalFilename: String,
        saveFileName: String,
        board: Board
    ) {
        file.transferTo(File(uploadeDir + saveFileName))
        val boardImage = BoardImage(originalFilename, saveFileName)
        board.addBoardImage(boardImage)
    }
}
