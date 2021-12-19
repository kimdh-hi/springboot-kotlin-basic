package com.pure.login.service

import com.pure.login.auth.JwtUtils
import com.pure.login.domain.User
import com.pure.login.dto.request.SigninRequest
import com.pure.login.dto.request.SignupRequest
import com.pure.login.repository.UserRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils) {

    @Transactional
    fun saveUser(signupRequest: SignupRequest): String {
        signupRequest.password = passwordEncoder.encode(signupRequest.password)
        val user = User(null, signupRequest.username, signupRequest.password)
        userRepository.save(user)

        return "회원가입 완료"
    }

    @Transactional(readOnly = true)
    fun signin(signinRequest: SigninRequest): String {
        val username = signinRequest.username
        val user = userRepository.findByUsername(username)
        user?.let {
            val matches = passwordEncoder.matches(signinRequest.password, it.password)
            if (!matches) throw BadCredentialsException("로그인 실패")
        } ?: throw BadCredentialsException("로그인 실패")

        return jwtUtils.generateToken(user.id as Long, user.username)
    }
}
