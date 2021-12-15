package com.example.lab2.controller

import com.example.lab2.security.UserDetailsImpl
import com.example.lab2.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/api/boards")
@RestController
class BoardController (private val boardService: BoardService) {

    @GetMapping
    fun getBoards() = ResponseEntity.ok().body(boardService.getBoards())

    @GetMapping("/{id}")
    fun getBoard(@PathVariable id: Long) = ResponseEntity.ok().body(boardService.getBoard(id))

    @PostMapping
    fun postBoard(
        @AuthenticationPrincipal userDetails: UserDetailsImpl,
        @RequestParam title: String,
        @RequestParam content: String,
        @RequestParam(required = false) file: MultipartFile?): ResponseEntity<String> {

        val member = userDetails.member
        boardService.saveBoard(member, title, content, file)
        return ResponseEntity.ok().body("저장완료")
    }
}