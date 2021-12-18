package com.dhk.ecommerce.user.service.dto.request

import com.dhk.ecommerce.user.domain.User

data class SignupRequestDto (
    var username: String,
    var password: String,
    var address: String,
    var detailAddress: String
)