package com.example.lab2

import org.junit.jupiter.api.Test

class TestClass {

    @Test
    fun test() {

        val a1 = A(10)

        println(a1.plus(10))
    }

    data class A(var count: Int)

    infix fun A.plus(x: Int): Int = this.count + x
}