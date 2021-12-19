package com.pure.login

import com.fasterxml.jackson.databind.ObjectMapper
import com.pure.login.domain.User
import com.pure.login.dto.request.SigninRequest
import com.pure.login.dto.request.SignupRequest
import com.pure.login.repository.UserRepository
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class UserTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder
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

        mockMvc.post("/user/signup")
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

        mockMvc.post("/user/signin")
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
    fun `권한없이 인증을 필요로하는 API에 접근 테스트`() {
        mockMvc.get("/user/me")
            .andDo {
                print()
            }
    }
}