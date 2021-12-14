package com.example.lab2.security

import com.example.lab2.domain.Member
import com.example.lab2.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val memberRepository: MemberRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val member: Member = memberRepository.findByUsername(username) ?: throw UsernameNotFoundException("존재하지 않는 username 입니다.")

        return UserDetailsImpl(member)
    }
}