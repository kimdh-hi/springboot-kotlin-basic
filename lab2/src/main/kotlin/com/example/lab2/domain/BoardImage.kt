package com.example.lab2.domain

import javax.persistence.*

@Entity
class BoardImage(path: String, board: Board) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    val path: String = path

    @ManyToOne(fetch = FetchType.LAZY)
    val board: Board = board
}