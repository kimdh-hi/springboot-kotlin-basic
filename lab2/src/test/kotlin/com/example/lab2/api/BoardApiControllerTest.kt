package com.example.lab2.api

import com.example.lab2.domain.Member
import com.example.lab2.repository.BoardImageRepository
import com.example.lab2.repository.MemberRepository
import com.example.lab2.utils.JwtUtils
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.mock.web.MockPart
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.multipart
import org.springframework.transaction.annotation.Transactional
import java.nio.charset.StandardCharsets

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BoardApiControllerTest {

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var memberRepository: MemberRepository
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var jwtUtils: JwtUtils
    @Autowired lateinit var boardImageRepository: BoardImageRepository

    lateinit var token: String
    lateinit var testMember: Member

    @BeforeAll
    fun beforeAll() {
        testMember = Member("test", passwordEncoder.encode("test"))
        memberRepository.save(testMember)
    }

    @BeforeEach
    fun beforeEach() {
        token = jwtUtils.createToken(testMember.username)
    }

    @Test
    @DisplayName("업로드 테스트")
    fun `Board 저장 - 파일업로드 포함`() {

        val imagePart = MockMultipartFile("file","test.txt" , "text/plain" , "hello file".byteInputStream(StandardCharsets.UTF_8))

        mockMvc.multipart("/api/boards")
        {
            file(imagePart)
                .part(MockPart("title", "title1".toByteArray(StandardCharsets.UTF_8)))
                .part(MockPart("content", "content1".toByteArray(StandardCharsets.UTF_8)))
            headers {
                header("Authorization", "bearer ".plus(token))
            }
        }
            .andDo {
                print()
            }
            .andExpect {
                status { isOk() }
            }

        val boardImages = boardImageRepository.findAll()

        Assertions.assertEquals(1, boardImages.size)
        Assertions.assertEquals("test.txt", boardImages.get(0).originalFileName)
    }
}