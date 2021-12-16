package com.example.lab2.api

import com.example.lab2.domain.Member
import com.example.lab2.dto.request.member.MemberDto
import com.example.lab2.dto.request.member.SigninDto
import com.example.lab2.repository.MemberRepository
import com.example.lab2.security.UserDetailsServiceImpl
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // BeforeAll
class MemberApiControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var memberRepository: MemberRepository
    @Autowired lateinit var objectMapper: ObjectMapper
    @Autowired lateinit var userDetailsServiceImpl: UserDetailsServiceImpl
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    lateinit var testMember: Member

    @BeforeAll
    fun beforeAll() {
        testMember = Member("test", passwordEncoder.encode("test"))
        memberRepository.save(testMember)
    }

    @WithMockUser
    @Test
    @DisplayName("GET /api/member/{memberId}")
    fun `회원 단건조회` () {
        mockMvc.get("/api/members/{memberId}", testMember.id)
            .andExpect {
                status {
                    isOk()
                }
            }
            .andDo { print() }
    }

    @Test
    @DisplayName("POST /api/members/signup")
    fun `회원가입` () {

        val memberDto: MemberDto = MemberDto("saveUsername", "savePassword")
        val memberDtoJson = objectMapper.writeValueAsString(memberDto)

        mockMvc.post("/api/members/signup")
        {
            contentType = MediaType.APPLICATION_JSON
            content = memberDtoJson
        }.andExpect {
            status { isCreated() }
        }.andDo { print() }
    }

    @WithMockUser
    @Test
    @DisplayName("PUT /api/member/{memberId}")
    fun `회원수정`() {


        val updateDto = MemberDto("updateUsername", "updatePassword")

        mockMvc.put("/api/members/{memberId}", testMember.id)
            {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateDto)
            }.andDo {
                print()
            }
            .andExpect {
                status { isOk() }
            }
    }

    @WithMockUser
    @Test
    @DisplayName("DELETE /api/member/{memberId}")
    fun `회원삭제` () {
        SecurityContextHolder.getContext().authentication =
            PreAuthenticatedAuthenticationToken(null, null,  AuthorityUtils.NO_AUTHORITIES)

        mockMvc.delete("/api/members/{memberId}", testMember.id)
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }

    @Test
    @DisplayName("로그인 테스트")
    fun `로그인 테스트 jwt 토큰발급 테스트` () {

        val signinDto: SigninDto = SigninDto("test", "test")
        val signinDtoJson = objectMapper.writeValueAsString(signinDto)

        mockMvc.post("/api/members/signin")
        {
            contentType = MediaType.APPLICATION_JSON
            content = signinDtoJson
        }
            .andExpect {
                status { isOk() }
            }
            .andDo {
                print()
            }
    }
}