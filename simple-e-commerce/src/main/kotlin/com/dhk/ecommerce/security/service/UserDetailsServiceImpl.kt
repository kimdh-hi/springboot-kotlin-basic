package com.dhk.ecommerce.security.service

import com.dhk.ecommerce.security.domain.UserDetailsImpl
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username) ?: throw UsernameNotFoundException("잘못된 사용자 이름 입니다.")

        return UserDetailsImpl(user)
    }
}