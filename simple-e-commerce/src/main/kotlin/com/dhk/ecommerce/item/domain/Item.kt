package com.dhk.ecommerce.item.domain

import javax.persistence.*

@Entity
class Item (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemId: Long? = null,
    @Column(nullable = false)
    var name: String,
    @Column(nullable = false)
    var description: String,
    @Column(nullable = false)
    var qty: Int,
)