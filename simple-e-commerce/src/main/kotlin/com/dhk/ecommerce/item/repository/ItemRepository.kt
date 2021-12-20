package com.dhk.ecommerce.item.repository

import com.dhk.ecommerce.item.domain.Item
import org.springframework.data.repository.CrudRepository

interface ItemRepository: CrudRepository<Item, Long> {

    fun findByNameContaining(name: String): List<String>?
}