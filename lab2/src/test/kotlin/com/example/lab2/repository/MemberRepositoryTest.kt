package com.example.lab2.repository

import com.example.lab2.domain.Member
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.transaction.Transactional

@Transactional
@DataJpaTest
class MemberRepositoryTest {

    @Autowired lateinit var memberRepository: MemberRepository

    @BeforeEach
    fun init() {
        val member1: Member = Member("member1", "password1" )
        val member2: Member = Member("member2", "password2" )
        val member3: Member = Member("member3", "password3" )

        memberRepository.saveAll(listOf(member1, member2, member3))
    }

    @Test
    fun `저장 후 조회 테스트`() {
        val member4: Member = Member("member4", "password4")
        memberRepository.save(member4)

        val member: Member? = memberRepository.findByUsername("member4")

        assertEquals(4, memberRepository.findAll().size)
        assertEquals("member4", memberRepository.findByUsername("member4")?.username)
    }

    @Test
    fun `username 조회 테스트`() {
        val username = "member1"
        val member = memberRepository.findByUsername(username)

        println(member?.username)

        assertEquals(username, member?.username)
    }
}