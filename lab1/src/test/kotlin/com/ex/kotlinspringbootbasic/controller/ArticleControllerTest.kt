package com.ex.kotlinspringbootbasic.controller

import com.ex.kotlinspringbootbasic.dto.request.ArticleRequestDto
import com.google.gson.Gson
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@AutoConfigureMockMvc
@SpringBootTest
internal class ArticleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @DisplayName("Article 전체조회 API")
    @Test
    fun 전체조회() {
        mockMvc.get("/api/articles")
            .andExpect {
                content { contentType(MediaType.APPLICATION_JSON) }
                status { isOk() }
            }
            .andDo {
                print()
            }
    }

    @DisplayName("Article 단건조회 API")
    @Test
    fun 단건조회() {
        mockMvc.get("/api/articles/{articleId}", 1L)
            .andExpect {
                status { isOk()}
                content {contentType(MediaType.APPLICATION_JSON)}
                jsonPath("$.title") { "article1" }
            }.andDo {
                print()
            }
    }

    @DisplayName("Article 저장 API")
    @Test
    fun 추가() {
        val articleDto = ArticleRequestDto("article4", "content4")
        val articleDtoJson:String = Gson().toJson(articleDto)

        mockMvc.post("/api/articles") {
            content = articleDtoJson
            contentType = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
        }.andDo {
            print()
        }
    }

    @DisplayName("Article 삭제 API")
    @Test
    fun 삭제() {
        mockMvc.delete("/api/articles/{articleId}", 3L)
            .andExpect {
                status { isOk() }
            }
            .andDo { print() }
    }

    @Test
    @DisplayName("Article 수정 API")
    fun 수정() {
        val updateRequestDto = ArticleRequestDto("updatedTitle", "updatedContent")
        val updateRequestDtoJosn = Gson().toJson(updateRequestDto)
        mockMvc.put("/api/articles/{articleId}", 1L)
            {
                contentType = MediaType.APPLICATION_JSON
                content = updateRequestDtoJosn
            }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }
}