package com.example.lab2.domain

import com.example.lab2.dto.request.BoardUpdateDto
import javax.persistence.*

@Entity
class Board(
    title: String,
    content: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var title: String = title
        protected set

    @Column(nullable = false)
    var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    var member: Member? = null

    fun addMember(member: Member){
        this.member = member
        member.boards.add(this)
    }

    fun updateBoard(updateDto: BoardUpdateDto) {
        this.title = updateDto.title
        this.content = updateDto.content
    }
}