package com.example.lab2.service

import com.example.lab2.domain.Member
import com.example.lab2.dto.request.MemberDto
import com.example.lab2.dto.response.MemberResponseDto
import com.example.lab2.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.util.stream.Collectors

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun saveMember(memberDto: MemberDto) = memberRepository.save(memberDto.toEntity())

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