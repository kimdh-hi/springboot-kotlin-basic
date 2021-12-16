package com.example.lab2.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class AppConfig(
    private val em: EntityManager
) {

    fun jpaQueryFactory() = JPAQueryFactory(em)
}