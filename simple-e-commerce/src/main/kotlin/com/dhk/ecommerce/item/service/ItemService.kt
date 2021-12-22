package com.dhk.ecommerce.item.service

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.item.service.dto.request.UpdateRequestDto
import com.dhk.ecommerce.item.service.dto.response.ItemDetailsResponseDto
import com.dhk.ecommerce.item.service.dto.response.ItemResponseDto
import com.dhk.ecommerce.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalArgumentException

@Service
@Transactional(readOnly = true)
class ItemService(private val itemRepository: ItemRepository) {

    // 목록조회 (페이징)
    fun getItemList(offset: Int, size: Int): List<ItemResponseDto> {
        val items: List<Item> = itemRepository.getItemList(offset, size)
        return itemsToItemsResponseDto(items)
    }

    // 상세정보 조회 (이미지 포함)
    fun getItemDetails(itemId: Long): ItemDetailsResponseDto {
        val item: Item? = itemRepository.getItemDetail(itemId)
        return item?.let {
            ItemDetailsResponseDto(
                it.name,
                it.description,
                it.price, it.stock,
                it.seller.username,
                it.thumbnailImage?.savedFileName ?: "No ThumbnailImage",
                it.itemImages.map {
                    it.savedFileName
                }
            )
        } ?: throw IllegalArgumentException("존재하지 않는 상품입니다.")
    }

    // 상품이름 조회
    fun searchItemByName(name: String, offset: Int, size: Int): List<ItemResponseDto> {
        val items = itemRepository.searchByName(name, offset, size)
        return itemsToItemsResponseDto(items)
    }

    // 상품정보 변경 (제목, 설명, 가격, 재고)
    @Transactional(readOnly = false)
    fun updateItem(userId: Long, itemId: Long, updateRequestDto: UpdateRequestDto) {
        if (!validItemOwner(userId, itemId)) throw IllegalArgumentException("상품을 수정할 수 없습니다.")

        val item = itemRepository.findByIdOrNull(itemId) ?: throw IllegalArgumentException("존재하지 않는 상품입니다.")
        item.update(updateRequestDto)
    }

    // 상품삭제
    private fun validItemOwner(userId: Long, itemId: Long): Boolean = itemRepository.validItemOwner(userId, itemId)

    private fun itemsToItemsResponseDto(items: List<Item>): List<ItemResponseDto> {
        return items.map {
            ItemResponseDto(it.name, it.description, it.price, it.stock, it.thumbnailImage?.savedFileName)
        }
    }
}