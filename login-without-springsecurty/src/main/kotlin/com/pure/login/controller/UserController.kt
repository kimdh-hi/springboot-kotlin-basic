package com.pure.login.controller

import com.pure.login.auth.LoginUser
import com.pure.login.domain.User
import com.pure.login.dto.request.SigninRequest
import com.pure.login.dto.request.SignupRequest
import com.pure.login.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/signup")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<String>
        = ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(signupRequest))

    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest): ResponseEntity<String>
        = ResponseEntity.ok().body(userService.signin(signinRequest))

    @GetMapping("/me")
    fun me(@LoginUser user: User?): ResponseEntity<User> {
        println("/users/me 호출")
        return ResponseEntity.ok().body(user)
    }
}