package com.example.lab2.api

import com.example.lab2.domain.Member
import com.example.lab2.repository.BoardImageRepository
import com.example.lab2.repository.BoardRepository
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
import org.springframework.test.web.servlet.get
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
    @Autowired lateinit var boardRepository: BoardRepository

    lateinit var token: String
    lateinit var testMember: Member

    @BeforeAll
    fun beforeAll() {
        testMember = Member("test", passwordEncoder.encode("test"))
        memberRepository.save(testMember)
        token = jwtUtils.createToken(testMember.username)
    }

    @Test
    @DisplayName("업로드 테스트")
    fun `Board 저장 - 파일업로드 포함`() {

        val imagePart = MockMultipartFile("file","test.txt" , "text/plain" , "hello file".byteInputStream(StandardCharsets.UTF_8))

        mockMvc.multipart("/api/boards/v1")
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

    @Test
    @DisplayName("업로드 테스트 - 파일 여러개")
    fun `Board 저장 - 여러개 파일 업로드`() {
        val file1 = MockMultipartFile("files", "test1.txt", "text/plain", "test1 - hello".byteInputStream(StandardCharsets.UTF_8))
        val file2 = MockMultipartFile("files", "test2.txt", "text/plain", "test2 - hello".byteInputStream(StandardCharsets.UTF_8))

        mockMvc.multipart("/api/boards/v1/files")
        {
            file(file1).file(file2)
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
                status {
                    isOk()
                }
            }

        val boardImages = boardImageRepository.findAll()

        Assertions.assertEquals(2, boardImages.size)
        Assertions.assertEquals("test1.txt", boardImages.get(0).originalFileName)
    }

    @Test
    @DisplayName("업로드 테스트 - S3 파일업로드")
    fun `Board 저장 - S3 파일업로드`() {
        val file1 = MockMultipartFile("files", "test1.txt", "text/plain", "test1 - hello".byteInputStream(StandardCharsets.UTF_8))
        val file2 = MockMultipartFile("files", "test2.txt", "text/plain", "test2 - hello".byteInputStream(StandardCharsets.UTF_8))

        mockMvc.multipart("/api/boards/v2")
        {
            file(file1).file(file2)
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
                status {
                    isOk()
                }
            }

        val boardImages = boardImageRepository.findAll()

        Assertions.assertEquals(2, boardImages.size)
        Assertions.assertEquals("test1.txt", boardImages.get(0).originalFileName)
        Assertions.assertEquals("test2.txt", boardImages.get(1).originalFileName)
    }

    @DisplayName("조회 테스트 - Board 상세정보 조회")
    @Test
    fun `조회 테스트 - Board 상세정보 조회` () {
        val file1 = MockMultipartFile("files", "test1.txt", "text/plain", "test1 - hello".byteInputStream(StandardCharsets.UTF_8))
        val file2 = MockMultipartFile("files", "test2.txt", "text/plain", "test2 - hello".byteInputStream(StandardCharsets.UTF_8))

        mockMvc.multipart("/api/boards/v2")
        {
            file(file1).file(file2)
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
                status {
                    isOk()
                }
            }

        val boardId = boardRepository.findAll().get(0).id
        val boardImages = boardImageRepository.findAll()

        Assertions.assertEquals(2, boardImages.size)
        Assertions.assertEquals("test1.txt", boardImages.get(0).originalFileName)
        Assertions.assertEquals("test2.txt", boardImages.get(1).originalFileName)

        mockMvc.get("/api/boards/{id}", boardId)
        {
            headers {
                header("Authorization", "bearer ".plus(token))
            }
        }.andDo {
            print()
        }.andExpect {
            status { isOk() }
        }
    }

}