package com.example.lab2.controller

import com.example.lab2.dto.request.BoardDto
import com.example.lab2.service.BoardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/boards")
@RestController
class BoardController (private val boardService: BoardService) {

    @GetMapping
    fun getBoards() = ResponseEntity.ok().body(boardService.getBoards())

    @GetMapping("/{id}")
    fun getBoard(@PathVariable id: Long) = ResponseEntity.ok().body(boardService.getBoard(id))

    @PostMapping
    fun postBoard(@RequestBody boardDto: BoardDto)  {
//        ResponseEntity.ok().body(boardService.saveBoard())
    }
}