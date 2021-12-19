package com.dhk.ecommerce.user.service

import com.dhk.ecommerce.security.utils.JwtTokenProvider
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import com.dhk.ecommerce.user.service.dto.request.LoginRequestDto
import com.dhk.ecommerce.user.service.dto.request.SignupRequestDto
import com.dhk.ecommerce.user.service.dto.response.TokenResponseDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
    ) {

    @Transactional
    fun saveUser(signupRequestDto: SignupRequestDto) {
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
    fun login(loginRequestDto: LoginRequestDto): TokenResponseDto {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequestDto.username, loginRequestDto.password, null)
            )
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("로그인 실패")
        }
        val token = jwtTokenProvider.generateToken(loginRequestDto.username)

        return TokenResponseDto(token)
    }
}
