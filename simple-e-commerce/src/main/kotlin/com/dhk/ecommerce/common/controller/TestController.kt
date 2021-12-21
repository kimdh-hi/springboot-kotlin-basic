package com.dhk.ecommerce.common.controller

import com.dhk.ecommerce.security.AuthenticatedUser
import com.dhk.ecommerce.user.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/test")
@RestController
class TestController {

    @GetMapping("/auth-test")
    fun test1(@AuthenticatedUser user: User): ResponseEntity<User> = ResponseEntity.ok().body(user)

    @GetMapping("/auth-test/seller")
    fun test2(@AuthenticatedUser user: User): ResponseEntity<User> = ResponseEntity.ok().body(user)
}