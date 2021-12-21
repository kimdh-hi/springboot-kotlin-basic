package com.dhk.ecommerce.helper

import com.dhk.ecommerce.itemImage.domain.ItemImage

class ItemImageTestHelper {

    companion object {

        fun createItemImage(originalFileName: String, savedFileName: String): ItemImage {
            return ItemImage(null, originalFileName, savedFileName)
        }
    }
}