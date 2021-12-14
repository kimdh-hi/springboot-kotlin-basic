package com.example.lab2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class Lab2Application

fun main(args: Array<String>) {
    runApplication<Lab2Application>(*args)
}
