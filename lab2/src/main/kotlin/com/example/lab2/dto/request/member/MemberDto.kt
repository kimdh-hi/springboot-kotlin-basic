package com.example.lab2.dto.request.member

import com.example.lab2.domain.Member

data class MemberDto (
    var username: String,
    var password: String
    ) {

    fun toEntity(): Member = Member(this.username, this.password)
}

