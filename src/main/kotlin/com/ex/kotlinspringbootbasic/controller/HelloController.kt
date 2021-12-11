package com.ex.kotlinspringbootbasic.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping("/api1")
    fun api1() : String {
        return "hello kotlin";
    }

    @GetMapping("/api2")
    fun api2() : String = "hello kotlin";

}