package com.dhk.ecommerce.user.controller

import com.dhk.ecommerce.security.AuthenticatedUser
import com.dhk.ecommerce.user.controller.dto.request.SigninRequest
import com.dhk.ecommerce.user.controller.dto.request.SignupRequest
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.service.UserService
import com.dhk.ecommerce.user.service.dto.request.SigninRequestDto
import com.dhk.ecommerce.user.service.dto.request.SignupRequestDto
import com.dhk.ecommerce.user.service.dto.response.TokenResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<String> {
        val signupRequestDto = SignupRequestDto(
            signupRequest.username, signupRequest.username, signupRequest.address, signupRequest.detailAddress
        )
        userService.signup(signupRequestDto)

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료")
    }

    @PostMapping("/signin")
    fun login(@RequestBody loginRequest: SigninRequest): ResponseEntity<TokenResponseDto> {
        val loginRequestDto = SigninRequestDto(loginRequest.username, loginRequest.password)
        val tokenResponseDto = userService.signin(loginRequestDto)

        return ResponseEntity.ok().body(tokenResponseDto)
    }

    @GetMapping("/me")
    fun me(@AuthenticatedUser user: User): ResponseEntity<User> {

        return ResponseEntity.ok().body(user)
    }
}