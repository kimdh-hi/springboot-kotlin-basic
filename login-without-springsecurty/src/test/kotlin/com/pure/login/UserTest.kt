package com.pure.login

import com.fasterxml.jackson.databind.ObjectMapper
import com.pure.login.domain.User
import com.pure.login.dto.request.SigninRequest
import com.pure.login.dto.request.SignupRequest
import com.pure.login.repository.UserRepository
import com.pure.login.service.UserService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import kotlin.math.sign

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class UserTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var userService: UserService
    lateinit var user: User

    @BeforeAll
    fun beforeAll() {
        user = User(null, "test", passwordEncoder.encode("test"))
        userRepository.save(user)
    }


    @DisplayName("회원가입")
    @Test
    fun `회원가입 테스트` () {
        val signupRequest = SignupRequest("username", "password")

        mockMvc.post("/users/signup")
        {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(signupRequest)
        }
            .andExpect {
                status { isCreated() }
            }
            .andDo {
                print()
            }
    }

    @DisplayName("인증 (토큰발급)")
    @Test
    fun `인증 테스트 토큰발급이 제대로 되는지` () {
        val signinRequest = SigninRequest("test", "test")

        mockMvc.post("/users/signin")
        {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(signinRequest)
        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }

    @DisplayName("접근불가 테스트")
    @Test
    fun `토큰없이 인증을 필요로하는 API에 접근 테스트`() {
        try {
            mockMvc.get("/users/me")
                .andDo {
                    print()
                }
                .andExpect {
                    status {
                        is5xxServerError()
                    }
                }
        } catch (e: Exception) {
            println(e)
        }
    }

    @DisplayName("토큰인증 테스트")
    @Test
    fun `발급된 토큰을 이용한 인증 테스트` () {
        val signinRequest = SigninRequest("test", "test")
        val token = userService.signin(signinRequest)

        mockMvc.get("/users/me")
        {
            header("Authorization", "Bearer " + token)
        }
            .andDo {
                print()
            }
            .andExpect {
                status { isOk() }
            }
    }
}