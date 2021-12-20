package com.dhk.ecommerce.helper

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.itemImage.domain.ItemImage
import com.dhk.ecommerce.user.domain.User

class ItemTestHelper {

    companion object {

        fun createItem(name: String, description: String, price: Int, stock: Int, seller: User, thumbnailImage: ItemImage): Item {
            return Item(null, name, description, price, stock, seller, thumbnailImage)
        }
    }
}