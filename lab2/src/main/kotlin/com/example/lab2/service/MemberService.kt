package com.example.lab2.service

import com.example.lab2.domain.Member
import com.example.lab2.dto.request.member.MemberDto
import com.example.lab2.dto.response.MemberResponseDto
import com.example.lab2.repository.MemberRepository
import com.example.lab2.utils.JwtUtils
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.util.stream.Collectors

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {

    @Transactional
    fun saveMember(memberDto: MemberDto) {
        memberDto.password = passwordEncoder.encode(memberDto.password)
        memberRepository.save(memberDto.toEntity())
    }

    @Transactional(readOnly = true)
    fun signin(memberDto: MemberDto): String {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(memberDto.username, memberDto.password, null)
            )
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("로그인 실패")
        }

        val token = jwtUtils.createToken(memberDto.username)

        return token
    }

    @Transactional(readOnly = true)
    fun getAllMembers(): List<MemberResponseDto> = memberRepository.findAll().stream().map { m -> MemberResponseDto(m.id, m.username) }.collect(Collectors.toList())

    @Transactional(readOnly = true)
    fun getMember(memberId: Long) : MemberResponseDto {
        // 일반적인 findById는 java의 Optional을 리턴한다.
        // findByIdOrNull과 엘비스 표현식으로 findById를 대신한다.
        val findMember: Member = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("존재하지 않는 ID 입니다.")

        return MemberResponseDto(findMember.id, findMember.username)
    }

    @Transactional
    fun updateMember(memberId: Long?, memberDto: MemberDto) {
        val findMember: Member = memberRepository.findByIdOrNull(memberId) ?: throw IllegalArgumentException("존재하지 않는 ID 입니다.")
        findMember.updateMember(memberDto)
    }

    @Transactional
    fun deleteMember(memberId: Long) = memberRepository.deleteById(memberId)
}