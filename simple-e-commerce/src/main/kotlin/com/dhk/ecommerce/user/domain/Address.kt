package com.dhk.ecommerce.user.domain

import javax.persistence.Embeddable

@Embeddable
class Address (
    var address: String,
    var detailAddress: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (address != other.address) return false
        if (detailAddress != other.detailAddress) return false

        return true
    }

    override fun hashCode(): Int {
        var result = address.hashCode()
        result = 31 * result + detailAddress.hashCode()
        return result
    }
}