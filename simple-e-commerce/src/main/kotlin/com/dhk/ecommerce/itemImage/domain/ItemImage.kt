package com.dhk.ecommerce.itemImage.domain

import com.dhk.ecommerce.item.domain.Item
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class ItemImage (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemImageId: Long? = null,
    @Column(nullable = false)
    var originalFileName: String,
    @Column(nullable = false)
    var savedFileName: String,
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var item: Item? = null
) {

    override fun toString(): String {
        return "ItemImage(itemImageId=$itemImageId, originalFileName='$originalFileName', savedFileName='$savedFileName')"
    }
}