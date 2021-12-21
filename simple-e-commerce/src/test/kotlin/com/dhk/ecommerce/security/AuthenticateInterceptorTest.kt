package com.dhk.ecommerce.security

import com.dhk.ecommerce.helper.UserTestHelper
import com.dhk.ecommerce.security.utils.JwtTokenProvider
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.repository.UserRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class AuthenticateInterceptorTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var jwtTokenProvider: JwtTokenProvider
    lateinit var user: User
    lateinit var token: String

    @BeforeAll
    fun beforeAll() {
        user = UserTestHelper.createUser("test", "test", "test", "test")
        userRepository.save(user)

        token = jwtTokenProvider.generateToken(user.userId as Long, user.username, user.role.toString())
    }


    @DisplayName("일반사용자 인증성공")
    @Test
    fun `일반 인증 성공` () {

        mockMvc.post("/items")
        {
            header("Authorization", "Bearer " + token)
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
}