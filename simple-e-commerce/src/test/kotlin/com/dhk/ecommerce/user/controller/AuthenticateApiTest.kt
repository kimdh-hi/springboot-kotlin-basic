package com.dhk.ecommerce.user.controller

import com.dhk.ecommerce.user.controller.dto.request.SignupRequest
import com.dhk.ecommerce.user.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class AuthenticateApiTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var userRepository: UserRepository

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
}