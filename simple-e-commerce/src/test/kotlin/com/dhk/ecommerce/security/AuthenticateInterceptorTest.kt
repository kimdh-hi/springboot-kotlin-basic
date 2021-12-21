package com.dhk.ecommerce.security

import com.dhk.ecommerce.helper.UserTestHelper
import com.dhk.ecommerce.security.utils.JwtTokenProvider
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import com.querydsl.core.util.MathUtils.result
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import javax.naming.AuthenticationException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class AuthenticateInterceptorTest {

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    lateinit var user: User
    lateinit var userToken: String

    lateinit var seller: User
    lateinit var sellerToken: String

    @BeforeAll
    fun beforeAll() {
        user = UserTestHelper.createUser("test", passwordEncoder.encode("test"), "test", "test")
        userRepository.save(user)
        userToken = jwtTokenProvider.generateToken(user.userId as Long, user.username, user.role.toString())

        seller =
            UserTestHelper.createSeller("seller", passwordEncoder.encode("seller"), "address", "detailsAddress")
        userRepository.save(seller)
        sellerToken = jwtTokenProvider.generateToken(seller.userId as Long, seller.username, seller.role.toString())
    }


    @DisplayName("일반사용자 인증성공")
    @Test
    fun `일반 인증 성공`() {

        mockMvc.get("/test/auth-test")
        {
            header("Authorization", "Bearer " + userToken)
        }
            .andDo {
                print()
            }
            .andExpect {
                status {
                    isOk()
                }
            }
    }


    @DisplayName("판매자 인증성공")
    @Test
    fun `판매자 인증 성공`() {

        mockMvc.get("/test/auth-test/seller")
        {
            header("Authorization", "Bearer " + sellerToken)
        }
            .andDo {
                print()
            }
            .andExpect {
                status {
                    isOk()
                }
            }
    }

    @DisplayName("일반사용자 인증실패")
    @Test
    fun `일반 인증 실패`() {

//        mockMvc.get("/test/auth-test")
//            .andDo {
//                print()
//            }
    }


    @DisplayName("판매자 인증실패")
    @Test
    fun `판매자 인증 실패`() {

//        mockMvc.get("/test/auth-test/seller")
//            .andDo {
//                print()
//            }

    }


}