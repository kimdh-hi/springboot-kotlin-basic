package com.dhk.ecommerce.itemImage.domain

import com.dhk.ecommerce.item.domain.Item
import javax.persistence.*

@Entity
class ItemImage (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemImageId: Long? = null,
    @Column(nullable = false)
    var originalFileName: String,
    @Column(nullable = false)
    var savedFileName: String,
    @ManyToOne(fetch = FetchType.LAZY)
    var item: Item
)