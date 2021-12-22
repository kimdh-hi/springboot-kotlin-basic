package com.dhk.ecommerce.item.service.dto.request

import org.springframework.web.multipart.MultipartFile

data class SaveRequestDto (
    var name: String,
    var description: String,
    var price: Int,
    var stock: Int,
    var thumbnailImage: MultipartFile,
    var itemImages: List<MultipartFile>
)