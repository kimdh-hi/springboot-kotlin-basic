package com.example.lab2.domain

import javax.persistence.*

@Entity
class BoardImage(
    originalFileName: String,
    saveFileName: String,
    board: Board) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    val saveFileName: String = saveFileName

    @Column(nullable = false)
    val originalFileName: String = originalFileName

    @ManyToOne(fetch = FetchType.LAZY)
    val board: Board = board
}