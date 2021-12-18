package com.dhk.ecommerce.user.service.UserService

import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import com.dhk.ecommerce.user.service.dto.request.SignupRequestDto
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
    ) {

    @Transactional
    fun saveUser(signupRequestDto: SignupRequestDto) {
        val user = User(
            null,
            signupRequestDto.username,
            passwordEncoder.encode(signupRequestDto.password),
            Address(signupRequestDto.address, signupRequestDto.detailAddress)
        )

        userRepository.save(user)
    }
}
