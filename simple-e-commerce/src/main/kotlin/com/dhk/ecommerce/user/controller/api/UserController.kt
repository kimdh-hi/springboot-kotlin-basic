package com.dhk.ecommerce.user.controller.api

import com.dhk.ecommerce.user.controller.dto.request.SignupRequest
import com.dhk.ecommerce.user.service.UserService.UserService
import com.dhk.ecommerce.user.service.dto.request.SignupRequestDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest) {
        val signupRequestDto = SignupRequestDto(
            signupRequest.username, signupRequest.username, signupRequest.address, signupRequest.detailAddress
        )

    }
}