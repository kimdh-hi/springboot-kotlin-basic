package com.example.lab2.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
            .antMatchers(HttpMethod.POST, "/api/users/signin").permitAll()
            .anyRequest().authenticated()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}