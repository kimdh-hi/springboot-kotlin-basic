package com.dhk.ecommerce.user.controller.api

import com.dhk.ecommerce.user.controller.dto.request.LoginRequest
import com.dhk.ecommerce.user.controller.dto.request.SignupRequest
import com.dhk.ecommerce.user.service.UserService
import com.dhk.ecommerce.user.service.dto.request.LoginRequestDto
import com.dhk.ecommerce.user.service.dto.request.SignupRequestDto
import com.dhk.ecommerce.user.service.dto.response.TokenResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<String> {
        val signupRequestDto = SignupRequestDto(
            signupRequest.username, signupRequest.username, signupRequest.address, signupRequest.detailAddress
        )
        userService.saveUser(signupRequestDto)

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<TokenResponseDto> {
        val loginRequestDto = LoginRequestDto(loginRequest.username, loginRequest.password)
        val tokenResponseDto = userService.login(loginRequestDto)

        return ResponseEntity.ok().body(tokenResponseDto)
    }
}