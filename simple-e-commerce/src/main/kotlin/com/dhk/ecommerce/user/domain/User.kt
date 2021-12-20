package com.dhk.ecommerce.user.domain

import com.dhk.ecommerce.common.domain.Timestamped
import javax.persistence.*

@Entity
class User  (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long? = null,
    @Column(nullable = false)
    var username: String,
    @Column(nullable = false)
    var nickname: String,
    @Column(nullable = false)
    var password: String,
    @Embedded
    var address: Address,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.ROLE_USER,

) : Timestamped()
