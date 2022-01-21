package com.comments.user.repository

import com.comments.user.domain.Account
import org.springframework.data.repository.CrudRepository

interface AccountRepository: CrudRepository<Account, Long> {
}