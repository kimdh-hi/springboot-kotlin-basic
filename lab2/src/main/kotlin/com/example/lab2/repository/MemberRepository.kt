package com.example.lab2.repository

import com.example.lab2.domain.Member
import org.apache.catalina.User
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByUsername(username: String) : Member?
}