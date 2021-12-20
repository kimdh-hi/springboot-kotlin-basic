package com.dhk.ecommerce.item.repository

import com.dhk.ecommerce.item.domain.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ItemRepository: JpaRepository<Item, Long>, ItemQueryRepository {

}