package com.dhk.ecommerce.item.service.dto.response

data class ItemDetailsResponseDto (
        var name: String,
        var description: String,
        var price: Int,
        var stock: Int,

        var sellerName: String,

        var thumbnailImagePath: String,
        var itemImagesPath: List<String>
)