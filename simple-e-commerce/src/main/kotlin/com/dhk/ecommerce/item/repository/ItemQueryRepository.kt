package com.dhk.ecommerce.item.repository

import com.dhk.ecommerce.item.domain.Item

interface ItemQueryRepository {

    // 상품 목록조회
    fun getItemList(offset: Int, limit: Int): List<Item>

    // 상품 상세정보 조회
    fun getItemDetail(itemId: Long): Item?
}