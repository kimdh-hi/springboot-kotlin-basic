package com.example.lab2.api

import com.example.lab2.domain.Member
import com.example.lab2.dto.request.MemberDto
import com.example.lab2.repository.MemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // BeforeAll
class MemberApiControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var memberRepository: MemberRepository
    @Autowired lateinit var objectMapper: ObjectMapper
    lateinit var testMember: Member

    @BeforeAll
    fun init() {
        testMember = Member("test", "test")
        memberRepository.save(testMember)
    }

    @Test
    @DisplayName("GET /api/member/{memberId}")
    fun `회원 단건조회` () {
        mockMvc.get("/api/members/{memberId}", testMember.id)
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                status { isOk() }
            }
            .andDo { print() }
    }

    @Test
    @DisplayName("POST /api/member/signup")
    fun `회원가입` () {

        val memberDto: MemberDto = MemberDto("saveUsername", "savePassword")
        val memberDtoJson = objectMapper.writeValueAsString(memberDto)

        mockMvc.post("/api/members")
        {
            contentType = MediaType.APPLICATION_JSON
            content = memberDtoJson
        }.andExpect {
            status { isCreated() }
        }.andDo { print() }
    }

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

    @Test
    @DisplayName("DELETE /api/member/{memberId}")
    fun `회원삭제` () {

        mockMvc.delete("/api/members/{memberId}", testMember.id)
    }
}