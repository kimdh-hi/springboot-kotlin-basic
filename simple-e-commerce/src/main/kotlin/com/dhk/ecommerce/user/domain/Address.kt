package com.dhk.ecommerce.user.domain

import javax.persistence.Embeddable

@Embeddable
class Address (
    var address: String,
    var detailAddress: String
)