package com.dhk.ecommerce.item.controller

import com.dhk.ecommerce.helper.ItemImageTestHelper
import com.dhk.ecommerce.helper.ItemTestHelper
import com.dhk.ecommerce.helper.UserTestHelper
import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.itemImage.domain.ItemImage
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.domain.UserRole
import com.dhk.ecommerce.user.repository.UserRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ItemControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var itemRepository: ItemRepository
    lateinit var item: Item

    @BeforeAll
    fun beforeAll() {
        val user = UserTestHelper.createUser("user1", passwordEncoder.encode("test"), "경기도 부천시 오정구", "우리아파트 105동 1304호")
        userRepository.save(user)
        val seller = UserTestHelper.createSeller("seller1", passwordEncoder.encode("test"), "서울특별시 강서구", "좋은아파트 103동 101호")
        userRepository.save(seller)

        val itemImage = ItemImageTestHelper.createItemImage("thumbnailFileName1", "thumbnailSavePath1")
        item = ItemTestHelper.createItem("item1", "item1 설명", 10_000, 100, seller, itemImage)


        val image1 = ItemImageTestHelper.createItemImage("originFileName1", "saveFilePath1")
        val image2 = ItemImageTestHelper.createItemImage("originFileName2", "saveFilePath2")
        val image3 = ItemImageTestHelper.createItemImage("originFileName3", "saveFilePath3")
        val image4 = ItemImageTestHelper.createItemImage("originFileName4", "saveFilePath4")
        item.addItemImage(image1)
        item.addItemImage(image2)
        item.addItemImage(image3)
        item.addItemImage(image4)

        item = itemRepository.save(item)

        val itemImage2 = ItemImageTestHelper.createItemImage("thumbnailFileName2", "thumbnailSavePath2")
        val item2 = ItemTestHelper.createItem("item2", "item2 설명", 20_000, 500, seller, itemImage2)
        itemRepository.save(item2)
    }

    @DisplayName("GET /items 상품 목록조회")
    @Test
    fun `목록조회` () {
        mockMvc.get("/items")
        {

        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }

    @DisplayName("GET /items/{itemId} 상품 상세조회")
    @Test
    fun `상세조회` () {
        mockMvc.get("/items/{itemId}", item.itemId)
            .andDo {
                print()
            }
            .andExpect {
                status { isOk() }
            }
    }
}