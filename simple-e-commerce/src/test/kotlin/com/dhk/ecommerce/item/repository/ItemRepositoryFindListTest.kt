package com.dhk.ecommerce.item.repository

import com.dhk.ecommerce.common.config.QuerydslConfig
import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.itemImage.domain.ItemImage
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.domain.UserRole
import com.dhk.ecommerce.user.repository.UserRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
@Import(QuerydslConfig::class)
class ItemRepositoryFindListTest {

    @Autowired lateinit var itemRepository: ItemRepository
    @Autowired lateinit var userRepository: UserRepository
    lateinit var item: Item

    @BeforeAll
    fun beforeAll() {
        val seller1 = userRepository.save(User(null, "seller1", "seller1", Address("test", "test"), UserRole.ROLE_SELLER))
        val seller2 = userRepository.save(User(null, "seller2", "seller2", Address("test", "test"), UserRole.ROLE_SELLER))
        val seller3 = userRepository.save(User(null, "seller3", "seller3", Address("test", "test"), UserRole.ROLE_SELLER))

        val items: MutableList<Item> = mutableListOf()
        for(i in 1..100) {
            if (i==1) {
                item = Item(null, "special-Item", "desc$i", 10, 1000, seller1, null)
                item.addItemImage(ItemImage(null, "og1", "sv1", null))
                item.addItemImage(ItemImage(null, "og2", "sv2", null))
                item.addItemImage(ItemImage(null, "og3", "sv3", null))

                items.add(item)
            }
            if (i<30) items.add(Item(null, "item$i", "desc$i", 10, 1000, seller1, null))
            else if (i < 60) items.add(Item(null, "item$i", "desc$i", 10, 3000, seller2, null))
            else items.add(Item(null, "item$i", "desc$i", 10, 2000, seller3, null))
        }
        itemRepository.saveAll(items)
    }

    @DisplayName("상세정보 조회")
    @Test
    fun `상품상세정보 조회` () {
        val findItem = itemRepository.getItemDetail(item.itemId as Long)

        assertEquals(item.itemId, findItem?.itemId)
        assertEquals(item.name, findItem?.name)
        assertEquals(item.itemImages.size, findItem?.itemImages?.size)
    }

    @DisplayName("이름 검색")
    @Test
    fun `상품 이름을 검색` () {
        val keyword = "special"

        val items = itemRepository.searchByName(keyword,0 , 10)

        assertEquals(1, items.size)
    }
}