package com.dhk.ecommerce.item.service

import com.dhk.ecommerce.common.dto.response.PageResponseV2
import com.dhk.ecommerce.common.utils.S3Utils
import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.item.service.dto.request.SaveRequestDto
import com.dhk.ecommerce.item.service.dto.request.UpdateRequestDto
import com.dhk.ecommerce.item.service.dto.response.ItemDetailsResponseDto
import com.dhk.ecommerce.item.service.dto.response.ItemResponseDto
import com.dhk.ecommerce.itemImage.domain.ItemImage
import com.dhk.ecommerce.user.domain.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.IllegalArgumentException

@Service
@Transactional(readOnly = true)
class ItemService(
    private val itemRepository: ItemRepository,
    private val s3Utils: S3Utils) {

    val s3Dir: String = "static"

    // 상품등록
    @Transactional(readOnly = false)
    fun saveItem(seller: User, dto: SaveRequestDto) {
        val thumbnailImage = dto.thumbnailImage
        val itemImages = dto.itemImages

        val item = Item(null, dto.name, dto.description, dto.price, dto.stock, seller, null)

        val thumbnailUploadedUrl = s3Utils.upload(thumbnailImage, s3Dir)
        val uploadedUrlItemImage = ItemImage(null, thumbnailImage.originalFilename as String, thumbnailUploadedUrl, null)
        item.thumbnailImage = uploadedUrlItemImage
        
        for (image in itemImages) {
            val uploadedUrl = s3Utils.upload(image, s3Dir)
            val itemImage = ItemImage(null, image.originalFilename as String, uploadedUrl, null)
            item.addItemImage(itemImage)
        }

        itemRepository.save(item)
    }

    // 목록조회 (페이징)
    fun getItemList(lastId: Long?, limit: Int, query: String?): PageResponseV2<ItemResponseDto> {
        query?.let {
            return getItemListBySearch(lastId, limit, query)
        }
        val items: List<Item> = itemRepository.getItemList(lastId, limit)
        val itemsToItemsResponseDto = items.map {
            ItemResponseDto(it.name, it.description, it.price, it.stock, it.thumbnailImage?.savedFileName)
        }
        val lastItemId = items.last().itemId

        return PageResponseV2<ItemResponseDto>(itemsToItemsResponseDto, lastItemId as Long)
    }

    private fun getItemListBySearch(lastId: Long?, limit: Int, query: String): PageResponseV2<ItemResponseDto> {
        val items = itemRepository.searchByName(query, lastId, limit)
        val itemsToItemsResponseDto = items.map {
            ItemResponseDto(it.name, it.description, it.price, it.stock, it.thumbnailImage?.savedFileName)
        }
        val lastItemId = items.last().itemId

        return PageResponseV2<ItemResponseDto>(itemsToItemsResponseDto, lastItemId as Long)
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

    // 상품정보 변경 (제목, 설명, 가격, 재고)
    @Transactional(readOnly = false)
    fun updateItem(userId: Long, itemId: Long, updateRequestDto: UpdateRequestDto) {
        if (!validItemOwner(userId, itemId)) throw IllegalArgumentException("상품을 수정할 수 없습니다.")

        val item = itemRepository.findByIdOrNull(itemId) ?: throw IllegalArgumentException("존재하지 않는 상품입니다.")
        item.update(updateRequestDto)
    }

    // 상품삭제
    @Transactional(readOnly = false)
    fun deleteItem(userId: Long, itemId: Long) {
        if (!validItemOwner(userId, itemId)) throw IllegalArgumentException("상품을 삭제할 수 없습니다.")

        itemRepository.deleteById(itemId)
    }


    private fun validItemOwner(userId: Long, itemId: Long): Boolean = itemRepository.validItemOwner(userId, itemId)

    private fun itemsToItemsResponseDto(items: List<Item>): List<ItemResponseDto> {
        return items.map {
            ItemResponseDto(it.name, it.description, it.price, it.stock, it.thumbnailImage?.savedFileName)
        }
    }
}