package com.dhk.ecommerce.item.controller

import com.dhk.ecommerce.helper.ItemTestHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class ItemControllerTest {

    @BeforeAll
    fun beforeAll() {

    }
}