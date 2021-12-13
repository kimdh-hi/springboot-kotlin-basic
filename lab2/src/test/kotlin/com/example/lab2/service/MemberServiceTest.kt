package com.example.lab2.service

import com.example.lab2.domain.Member
import com.example.lab2.dto.request.MemberDto
import com.example.lab2.dto.response.MemberResponseDto
import com.example.lab2.repository.MemberRepository
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@Transactional
@SpringBootTest
internal class MemberServiceTest {

    @Autowired lateinit var memberRepository: MemberRepository
    @Autowired lateinit var memberService: MemberService
    lateinit var member: Member

    @BeforeEach
    fun init() {
        member = Member("member0", "password0")
        memberRepository.save(member)
    }

    @DisplayName("회원가입")
    @Test
    fun `회원가입` () {
        val memberDto = MemberDto("member", "password")
        memberService.saveMember(memberDto)

        assertEquals(1, memberRepository.findAll().size)
    }

    @DisplayName("단건조회")
    @Test
    fun `PK 단건조회`() {
        val id: Long = member.id as Long
        val findMember: MemberResponseDto = memberService.getMember(id)

        print(findMember)
        assertEquals("member0", findMember.username)
    }

    @Test
    @DisplayName("수정")
    fun `username, password 수정`() {
        val updateDto = MemberDto("updateUsername", "updatePassword")

        memberService.updateMember(member.id, updateDto)

        assertEquals("updateUsername", member.username)
        assertEquals("updatePassword", member.password)
    }

    @Test
    @DisplayName("삭제")
    fun `삭제`() {
        val id: Long = member.id as Long
        memberService.deleteMember(id)

        assertEquals(false, memberRepository.existsById(id))
    }
}