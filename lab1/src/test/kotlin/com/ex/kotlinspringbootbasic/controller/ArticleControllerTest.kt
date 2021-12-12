package com.ex.kotlinspringbootbasic.controller

import com.ex.kotlinspringbootbasic.dto.request.AddArticleDto
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity.post
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

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
        val articleDto = AddArticleDto("article4", "content4")
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
}