package com.dhk.ecommerce.item.controller

import com.dhk.ecommerce.common.dto.response.PageResponseV2
import com.dhk.ecommerce.item.service.dto.request.SaveRequestDto
import com.dhk.ecommerce.item.controller.dto.request.UpdateRequest
import com.dhk.ecommerce.item.service.ItemService
import com.dhk.ecommerce.item.service.dto.response.ItemDetailsResponseDto
import com.dhk.ecommerce.item.service.dto.response.ItemResponseDto
import com.dhk.ecommerce.security.AuthenticatedUser
import com.dhk.ecommerce.user.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/items")
@RestController
class ItemController(private val itemService: ItemService) {

    @GetMapping
    fun getItems(
        @RequestParam(defaultValue = "5") size: Int,
        @RequestParam(required = false) lastId: Long?
    ): ResponseEntity<PageResponseV2<ItemResponseDto>> = ResponseEntity.ok().body(itemService.getItemList(lastId, size))

    @GetMapping("/{itemId}")
    fun getItemDetails(
        @PathVariable itemId: Long
    ): ResponseEntity<ItemDetailsResponseDto> = ResponseEntity.ok().body(itemService.getItemDetails(itemId))

    @PostMapping
    fun saveItem(@AuthenticatedUser user: User,
                 @RequestParam name: String, @RequestParam description: String, @RequestParam price: Int, @RequestParam stock: Int,
                 @RequestParam thumbnailImage: MultipartFile, @RequestParam itemImages: List<MultipartFile>): ResponseEntity<String> {

        val saveRequestDto = SaveRequestDto(name, description, price, stock, thumbnailImage, itemImages)
        itemService.saveItem(user, saveRequestDto)

        return ResponseEntity.ok().body("ok")
    }

    @PutMapping("/{itemId}")
    fun updateItem(
        @AuthenticatedUser user: User, @PathVariable itemId: Long, @RequestBody updateRequest: UpdateRequest): ResponseEntity<String> {

        val dto = updateRequest.toServiceDto()
        itemService.updateItem(user.userId as Long, itemId, dto)

        return ResponseEntity.ok().body("수정완료")
    }

    @DeleteMapping("/{itemId}")
    fun deleteItem(@AuthenticatedUser user: User, @PathVariable itemId: Long): ResponseEntity<String> {

        itemService.deleteItem(user.userId as Long, itemId)

        return ResponseEntity.ok().body("삭제완료")
    }


}