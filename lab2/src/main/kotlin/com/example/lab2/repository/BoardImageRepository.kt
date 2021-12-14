package com.example.lab2.repository

import com.example.lab2.domain.BoardImage
import org.springframework.data.jpa.repository.JpaRepository

interface BoardImageRepository: JpaRepository<BoardImage, Long> {
}