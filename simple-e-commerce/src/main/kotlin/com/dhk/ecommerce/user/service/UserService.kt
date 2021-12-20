package com.dhk.ecommerce.user.service

import com.dhk.ecommerce.security.utils.JwtTokenProvider
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import com.dhk.ecommerce.user.service.dto.request.SigninRequestDto
import com.dhk.ecommerce.user.service.dto.request.SignupRequestDto
import com.dhk.ecommerce.user.service.dto.response.TokenResponseDto
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
    ) {

    @Transactional
    fun signup(signupRequestDto: SignupRequestDto) {
        signupRequestDto.password = passwordEncoder.encode(signupRequestDto.password)
        val user = User(
            null,
            signupRequestDto.username,
            signupRequestDto.password,
            Address(signupRequestDto.address, signupRequestDto.detailAddress)
        )

        userRepository.save(user)
    }

    @Transactional(readOnly = true)
    fun signin(loginRequestDto: SigninRequestDto): TokenResponseDto {
        val user: User? = userRepository.findByUsername(loginRequestDto.username)
        user?.let {
            val matches = passwordEncoder.matches(loginRequestDto.password, user.password)
            if (!matches) throw BadCredentialsException("인증실패")
        } ?: throw UsernameNotFoundException("존재하지 않는 ID 입니다.")

        return TokenResponseDto(jwtTokenProvider.generateToken(user.userId as Long, user.username))
    }
}
