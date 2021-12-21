package com.dhk.ecommerce.item.controller

import com.dhk.ecommerce.item.service.ItemService
import com.dhk.ecommerce.item.service.dto.response.ItemDetailsResponseDto
import com.dhk.ecommerce.item.service.dto.response.ItemResponseDto
import com.dhk.ecommerce.security.AuthenticatedUser
import com.dhk.ecommerce.user.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/items")
@RestController
class ItemController(private val itemService: ItemService) {

    @GetMapping
    fun getItems(
        @RequestParam(defaultValue = "0") offset: Int, @RequestParam(defaultValue = "10") size: Int
    ) : ResponseEntity<List<ItemResponseDto>> = ResponseEntity.ok().body(itemService.getItemList(offset, size))

    @GetMapping("/{itemId}")
    fun getItemDetails(
        @PathVariable itemId: Long
    ): ResponseEntity<ItemDetailsResponseDto> = ResponseEntity.ok().body(itemService.getItemDetails(itemId))

    @PostMapping
    fun postItem(@AuthenticatedUser user: User): String {
        println(user)
        return "ok"
    }
}