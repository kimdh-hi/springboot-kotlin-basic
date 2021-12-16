package com.example.lab2.dto.request.board

import org.springframework.web.multipart.MultipartFile

data class BoardSaveDto (
    var title: String,
    var content: String,
    )