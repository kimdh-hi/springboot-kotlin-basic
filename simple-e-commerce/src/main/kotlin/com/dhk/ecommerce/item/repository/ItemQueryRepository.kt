package com.dhk.ecommerce.item.repository

import com.dhk.ecommerce.item.domain.Item

interface ItemQueryRepository {

    // 상품 목록조회
    fun getItemList(lastItemId: Long?, limit: Int): List<Item>

    // 상품 상세정보 조회
    fun getItemDetail(itemId: Long): Item?

    // 상품이름으로 검색
    fun searchByName(name: String, lastItemId: Long?, limit: Int): List<Item>

    // 상품의 주인인지 판별
    fun validItemOwner(userId: Long, itemId: Long): Boolean
}