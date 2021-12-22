package com.dhk.ecommerce.item.controller.dto.request

import org.springframework.web.multipart.MultipartFile

data class SaveRequest (
    var name: String,
    var description: String,
    var price: Int,
    var stock: Int,
    var thumbnailImage: MultipartFile,
    var itemImages: List<MultipartFile>
)