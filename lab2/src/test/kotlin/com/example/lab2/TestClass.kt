package com.example.lab2

import org.junit.jupiter.api.Test

class TestClass {

    @Test
    fun test() {
        var str: String? = "str"
        println(str?.length)

        print(str!!.length)
    }
}