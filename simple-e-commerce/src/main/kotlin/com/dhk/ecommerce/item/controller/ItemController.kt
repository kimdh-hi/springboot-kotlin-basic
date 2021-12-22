package com.dhk.ecommerce.item.controller

import com.dhk.ecommerce.item.controller.dto.request.SaveRequest
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
        @RequestParam(defaultValue = "0") offset: Int, @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<List<ItemResponseDto>> = ResponseEntity.ok().body(itemService.getItemList(offset, size))

    @GetMapping("/{itemId}")
    fun getItemDetails(
        @PathVariable itemId: Long
    ): ResponseEntity<ItemDetailsResponseDto> = ResponseEntity.ok().body(itemService.getItemDetails(itemId))

    @PostMapping
    fun saveItem(@AuthenticatedUser user: User,
                 @RequestParam name: String, @RequestParam description: String, @RequestParam price: Int, @RequestParam stock: Int,
                 @RequestParam thumbnailImage: MultipartFile, @RequestParam itemImages: List<MultipartFile>): ResponseEntity<String> {

        val saveRequest = SaveRequest(name, description, price, stock, thumbnailImage, itemImages)


        return ResponseEntity.ok().body("ok")
    }

    @PutMapping("/{itemId}")
    fun updateItem(
        @AuthenticatedUser user: User, @PathVariable itemId: Long, @RequestBody updateRequest: UpdateRequest): ResponseEntity<String> {

        val dto = updateRequest.toServiceDto()
        itemService.updateItem(user.userId as Long, itemId, dto)

        return ResponseEntity.ok().body("수정완료")
    }
}