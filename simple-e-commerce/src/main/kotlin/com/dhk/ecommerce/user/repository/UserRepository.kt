package com.dhk.ecommerce.user.repository

import com.dhk.ecommerce.user.domain.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {

    fun findByUsername(username: String): User?
}