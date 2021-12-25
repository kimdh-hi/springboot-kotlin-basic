package com.dhk.ecommerce.order.controller

import com.dhk.ecommerce.helper.ItemImageTestHelper
import com.dhk.ecommerce.helper.ItemTestHelper
import com.dhk.ecommerce.helper.UserTestHelper
import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.orders.domain.OrderStatus
import com.dhk.ecommerce.orders.repository.OrderRepository
import com.dhk.ecommerce.orders.service.OrderService
import com.dhk.ecommerce.orders.service.dto.request.OrderItemRequestDto
import com.dhk.ecommerce.orders.service.dto.request.OrderRequestDto
import com.dhk.ecommerce.security.utils.JwtTokenProvider
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class OrdersControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var itemRepository: ItemRepository
    @Autowired lateinit var orderRepository: OrderRepository
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider

    lateinit var user: User
    lateinit var userToken: String
    lateinit var item: Item
    val AUTH_HEADER = "Authorization"
    val BEARER_PREFIX = "Bearer "

    @BeforeAll
    fun beforeAll() {
        val seller = UserTestHelper.createSeller("seller", "seller", "sellerAddress", "sellerDetailAddress")
        user = UserTestHelper.createSeller("user", "user", "userAddress", "userDetailAddress")
        userRepository.saveAll(listOf(seller, user))

        userToken = jwtTokenProvider.generateToken(user.userId as Long, user.username, user.role.toString())

        val itemImage = ItemImageTestHelper.createItemImage("thumbnailFileName2", "thumbnailSavePath2")
        item = ItemTestHelper.createItem("item2", "item2 설명", 20_000, 500, seller, itemImage)
        itemRepository.save(item)
    }

    @DisplayName("주문 테스트")
    @Test
    fun `주문하기` () {

        val orderItemRequestDto = OrderItemRequestDto(item.itemId as Long, 18000, 10)
        val orderRequestDto = OrderRequestDto(listOf(orderItemRequestDto), true, "", "")

        mockMvc.post("/orders")
        {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(orderRequestDto)
            header(AUTH_HEADER, BEARER_PREFIX + userToken)
        }
            .andDo {
                print()
            }
            .andExpect {
                status { isOk() }
            }

        val testItem = itemRepository.findById(item.itemId as Long).get()
        val testOrder = orderRepository.findAll()

        assertEquals(testItem.stock, item.stock - 10)
        assertEquals(1, testOrder.size)
        assertEquals(user.address, testOrder.get(0).address)
    }
}