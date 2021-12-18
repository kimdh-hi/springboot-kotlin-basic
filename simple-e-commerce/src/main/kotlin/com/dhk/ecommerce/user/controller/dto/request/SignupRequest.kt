package com.dhk.ecommerce.user.controller.dto.request

data class SignupRequest (
    var username: String,
    var password: String,
    var address: String,
    var detailAddress: String
)