package com.dhk.ecommerce.item.repository

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.domain.QItem.item
import com.dhk.ecommerce.itemImage.domain.QItemImage
import com.dhk.ecommerce.itemImage.domain.QItemImage.itemImage
import com.dhk.ecommerce.user.domain.QUser
import com.dhk.ecommerce.user.domain.QUser.user
import com.querydsl.jpa.impl.JPAQueryFactory

class ItemQueryRepositoryImpl(private val query: JPAQueryFactory): ItemQueryRepository {

    /**
     * 상품 목록조회
     */
    override fun getItemList(offset: Int, limit: Int): List<Item> {
        return query.select(item)
            .from(item)
            .leftJoin(item.thumbnailImage, itemImage)
            .offset(offset.toLong())
            .limit(limit.toLong())
            .orderBy(item.createdAt.desc())
            .fetch()
    }

    /**
     * 상품 상세정보 조회
     */
    override fun getItemDetail(itemId: Long): Item? {
        return query.select(item)
            .from(item)
            .join(item.seller, user).fetchJoin()
            .join(item.itemImages, itemImage).fetchJoin()
            .where(item.itemId.eq(itemId))
            .fetchOne()
    }
}