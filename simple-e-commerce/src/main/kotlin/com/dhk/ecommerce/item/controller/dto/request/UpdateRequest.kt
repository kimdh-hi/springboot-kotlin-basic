package com.dhk.ecommerce.item.controller.dto.request

import com.dhk.ecommerce.item.service.dto.request.UpdateRequestDto

data class UpdateRequest (
    var name: String?,
    var description: String?,
    var price: Int?,
    var stock: Int?
) {

    fun toServiceDto(): UpdateRequestDto = UpdateRequestDto(this.name, this.description, this.price, this.stock)
}
