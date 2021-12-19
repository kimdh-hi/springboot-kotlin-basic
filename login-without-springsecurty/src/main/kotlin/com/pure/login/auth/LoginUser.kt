package com.pure.login.auth

@Target(AnnotationTarget.VALUE_PARAMETER) // 생성자 매개변수에 적용
@Retention(AnnotationRetention.RUNTIME)
annotation class LoginUser()