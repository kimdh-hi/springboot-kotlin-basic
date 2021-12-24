package com.dhk.ecommerce.item.controller

import com.dhk.ecommerce.helper.ItemImageTestHelper
import com.dhk.ecommerce.helper.ItemTestHelper
import com.dhk.ecommerce.helper.UserTestHelper
import com.dhk.ecommerce.item.controller.dto.request.UpdateRequest
import com.dhk.ecommerce.item.domain.Item
import com.dhk.ecommerce.item.repository.ItemRepository
import com.dhk.ecommerce.security.utils.JwtTokenProvider
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockPart
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets

@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ItemControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var itemRepository: ItemRepository
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider
    lateinit var item: Item
    lateinit var user: User
    lateinit var userToken: String
    lateinit var seller: User
    lateinit var sellerToken: String
    val AUTH_HEADER = "Authorization"
    val BEARER_PREFIX = "Bearer "

    @BeforeAll
    fun beforeAll() {
        user = UserTestHelper.createUser("user1", passwordEncoder.encode("test"), "경기도 부천시 오정구", "우리아파트 105동 1304호")
        userRepository.save(user)
        userToken = jwtTokenProvider.generateToken(user.userId as Long, user.username, user.role.toString())

        seller = UserTestHelper.createSeller("seller1", passwordEncoder.encode("test"), "서울특별시 강서구", "좋은아파트 103동 101호")
        userRepository.save(seller)
        sellerToken = jwtTokenProvider.generateToken(seller.userId as Long, seller.username, seller.role.toString())

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
        val itemImage3 = ItemImageTestHelper.createItemImage("thumbnailFileName2", "thumbnailSavePath2")
        val itemImage4 = ItemImageTestHelper.createItemImage("thumbnailFileName2", "thumbnailSavePath2")
        val itemImage5 = ItemImageTestHelper.createItemImage("thumbnailFileName2", "thumbnailSavePath2")
        val itemImage6 = ItemImageTestHelper.createItemImage("thumbnailFileName2", "thumbnailSavePath2")
        val itemImage7 = ItemImageTestHelper.createItemImage("thumbnailFileName2", "thumbnailSavePath2")
        val item2 = ItemTestHelper.createItem("item2", "item2 설명", 20_000, 500, seller, itemImage2)
        val item3 = ItemTestHelper.createItem("iphone10", "item3 설명", 20_000, 500, seller, itemImage3)
        val item4 = ItemTestHelper.createItem("item4", "item4 설명", 20_000, 500, seller, itemImage4)
        val item5 = ItemTestHelper.createItem("item5", "item5 설명", 20_000, 500, seller, itemImage5)
        val item6 = ItemTestHelper.createItem("item6", "item6 설명", 20_000, 500, seller, itemImage6)
        val item7 = ItemTestHelper.createItem("item7", "item7 설명", 20_000, 500, seller, itemImage7)
        itemRepository.save(item2)
        itemRepository.save(item3)
        itemRepository.save(item4)
        itemRepository.save(item5)
        itemRepository.save(item6)
        itemRepository.save(item7)
    }

    @DisplayName("GET /items 상품 목록조회")
    @Test
    fun `목록조회` () {
        mockMvc.get("/items?lastId=3")
        {

        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }

    @Test
    @DisplayName("/GET /items 상품 목록검색")
    fun `검색조회` () {
        val query: String = "iphone"
        mockMvc.get("/items?search=${query}")
            .andDo {
                print()
            }
            .andExpect {
                status {
                    isOk()
                }
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

    @DisplayName("POST /items 상품등록")
    @Test
    fun `상품등록` () {
        val thumbnailImage = MockMultipartFile("thumbnailImage", "thumbnailImageFileName", "plain/text", "thumbnailImageTest".byteInputStream(StandardCharsets.UTF_8))
        val itemImage1 = MockMultipartFile("itemImages", "itemImageFileName1", "plain/text", "itemImage1".byteInputStream(StandardCharsets.UTF_8))
        val itemImage2 = MockMultipartFile("itemImages", "itemImageFileName2", "plain/text", "itemImage2".byteInputStream(StandardCharsets.UTF_8))
        val itemImage3 = MockMultipartFile("itemImages", "itemImageFileName3", "plain/text", "itemImage3".byteInputStream(StandardCharsets.UTF_8))

        mockMvc.multipart("/items")
        {
            header(AUTH_HEADER, BEARER_PREFIX + sellerToken)
            contentType = MediaType.MULTIPART_FORM_DATA
            file(thumbnailImage)
            file(itemImage1)
            file(itemImage2)
            file(itemImage3)
            part(MockPart("name", "testName".toByteArray()))
            part(MockPart("description", "desc".toByteArray()))
            part(MockPart("price", "1000".toByteArray()))
            part(MockPart("stock", "10".toByteArray()))
        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }

    @DisplayName("PUT /items/{itemId} 상품수정")
    @Test
    fun `상품수정` () {
        val updateName = "updateName"
        val updateDescription = "updateDescription"
        val updatePrice = 999
        val updateStock = 999
        val updateRequest = UpdateRequest(updateName, updateDescription, updatePrice, updateStock)
        val updateRequestJson = objectMapper.writeValueAsString(updateRequest)

        mockMvc.put("/items/{itemId}", item.itemId)
        {
            contentType = MediaType.APPLICATION_JSON
            content = updateRequestJson
            headers {
                header(AUTH_HEADER, BEARER_PREFIX + sellerToken)
            }
        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }

        val item = itemRepository.findByIdOrNull(item.itemId) as Item

        Assertions.assertEquals(updateName, item.name)
        Assertions.assertEquals(updateDescription, item.description)
        Assertions.assertEquals(updatePrice, item.price)
        Assertions.assertEquals(updateStock, item.stock)
    }

    @DisplayName("DELETE /items/{itemId} 상품삭제")
    @Test
    fun `상품삭제` () {

        mockMvc.delete("/items/{itemId}", item.itemId)
        {
            header(AUTH_HEADER, BEARER_PREFIX + sellerToken)
        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }
}