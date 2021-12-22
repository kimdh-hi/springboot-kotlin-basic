package com.dhk.ecommerce.item.domain

import com.dhk.ecommerce.common.domain.Timestamped
import com.dhk.ecommerce.item.service.dto.request.UpdateRequestDto
import com.dhk.ecommerce.itemImage.domain.ItemImage
import com.dhk.ecommerce.user.domain.User
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
    var price: Int,

    @Column(nullable = false)
    var stock: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    var seller: User,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    var thumbnailImage: ItemImage?,

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
    var itemImages: MutableSet<ItemImage> = mutableSetOf()
): Timestamped() {

    // 상품정보 변경 (제목, 설명, 가격, 수량)
    fun update(updateRequestDto: UpdateRequestDto) {
        updateRequestDto.name?.let { this.name = it }
        updateRequestDto.description?.let { this.description = it }
        updateRequestDto.price?.let { this.price = it }
        updateRequestDto.stock?.let { this.stock = it }
    }

    // 재고감소
    fun decreaseStock() {
        if (this.stock <= 0) throw RuntimeException("재고가 부족합니다.")
        --this.stock
    }
    // 재고증가
    fun increaseStock() = ++this.stock

    // 양방향 연관관계 편의 메서드
    fun addItemImage(itemImage: ItemImage) {
        this.itemImages.add(itemImage)
        itemImage.item = this
    }
}