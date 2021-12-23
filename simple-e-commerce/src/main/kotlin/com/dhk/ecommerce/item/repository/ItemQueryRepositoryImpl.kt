package com.dhk.ecommerce.item.repository

import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.domain.QItem.item
import com.dhk.ecommerce.itemImage.domain.QItemImage
import com.dhk.ecommerce.itemImage.domain.QItemImage.itemImage
import com.dhk.ecommerce.user.domain.QUser
import com.dhk.ecommerce.user.domain.QUser.user
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory

class ItemQueryRepositoryImpl(private val query: JPAQueryFactory): ItemQueryRepository {

    /**
     * 상품 목록조회
     */
    override fun getItemList(lastItemId: Long?, limit: Int): List<Item> {
        return query.select(item)
            .from(item)
            .leftJoin(item.thumbnailImage, itemImage).fetchJoin()
            .where(ltItemId(lastItemId))
            .limit(limit.toLong())
            .orderBy(item.itemId.desc())
            .fetch()
    }

    /**
     * 상품 상세정보 조회
     */
    override fun getItemDetail(itemId: Long): Item? {
        return query.select(item).distinct()
            .from(item)
            .join(item.seller, user).fetchJoin()
            .leftJoin(item.thumbnailImage, itemImage).fetchJoin()
            .leftJoin(item.itemImages, itemImage).fetchJoin()
            .where(item.itemId.eq(itemId))
            .fetchOne()
    }

    /**
     * 상품 이름으로 검색
     */
    override fun searchByName(name: String, offset: Int, limit: Int): List<Item> {
        return query.select(item)
            .from(item)
            .leftJoin(item.thumbnailImage, itemImage).fetchJoin()
            .where(item.name.contains(name))
            .offset(offset.toLong())
            .limit(limit.toLong())
            .orderBy(item.createdAt.desc())
            .fetch()
    }

    /**
     * 상품의 주인인지 판별
     */
    override fun validItemOwner(userId: Long, itemId: Long): Boolean {
        val result = query.selectOne()
            .from(item)
            .where(item.itemId.eq(itemId).and(item.seller.userId.eq(userId)))
            .fetchFirst()

        return result != null
    }

    fun ltItemId(itemId: Long?): BooleanExpression? {
        itemId?.let {
            return item.itemId.lt(itemId)
        } ?: return null
    }
}