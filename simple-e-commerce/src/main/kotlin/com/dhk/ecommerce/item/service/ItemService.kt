package com.dhk.ecommerce.item.service

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.item.service.dto.response.ItemResponseDto
import org.springframework.stereotype.Service

@Service
class ItemService(private val itemRepository: ItemRepository) {

    // 목록조회 (페이징)
    fun itemList(offset: Int, size: Int): List<ItemResponseDto> {
        val items: List<Item> = itemRepository.getItemList(offset, size)
        return items.map {
            ItemResponseDto(it.name, it.description, it.price, it.stock, it.thumbnailImage?.savedFileName)
        }
    }

    // 상세정보 조회 (이미지 포함)

    // PK 조회

    // 상품이름 조회

    // 상품정보 변경 (제목, 설명)

    // 상품삭제
}