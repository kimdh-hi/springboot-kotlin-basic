package com.dhk.ecommerce.item.controller

import com.dhk.ecommerce.item.service.ItemService
import com.dhk.ecommerce.item.service.dto.response.ItemResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/items")
@RestController
class ItemController(private val itemService: ItemService) {

    @GetMapping
    fun getItems(
        @RequestParam(defaultValue = "0") offset: Int, @RequestParam(defaultValue = "10") size: Int
    ) : ResponseEntity<List<ItemResponseDto>> = ResponseEntity.ok().body(itemService.itemList(offset, size))
}