package com.dhk.ecommerce.user.controller

import com.dhk.ecommerce.user.controller.dto.request.LoginRequest
import com.dhk.ecommerce.user.controller.dto.request.SignupRequest
import com.dhk.ecommerce.user.domain.Address
import com.dhk.ecommerce.user.domain.User
import com.dhk.ecommerce.user.domain.UserRole
import com.dhk.ecommerce.user.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class AuthenticateApiTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var passwordEncoder: BCryptPasswordEncoder
    lateinit var user: User

    @BeforeAll
    fun beforeAll() {
        user = User(
            null, "aaa", passwordEncoder.encode("aaa"), Address("address", "detail"), UserRole.ROLE_USER
        )
        userRepository.save(user)
    }

    @DisplayName("회원가입")
    @Test
    fun `회원가입 테스트` () {

        val signupRequest = SignupRequest("test", "test", "address", "detailAddress")

        mockMvc.post("/signup")
        {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(signupRequest)
        }
            .andExpect {
                status {
                    isCreated()
                }
            }
            .andDo {
                print()

            }
    }

    @Test
    @DisplayName("토큰발급")
    fun `토큰발급 테스트`() {
        val loginRequest = LoginRequest("aaa", "aaa")

        mockMvc.post("/login")
        {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(loginRequest)
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