package com.example.lab2.dto.response.board

import com.querydsl.core.annotations.QueryProjection

data class BoardDetailsDto  (
    var title: String,
    var content: String,
    var fileNames: List<String>?
)
