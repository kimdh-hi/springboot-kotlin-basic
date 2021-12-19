package com.pure.login.domain

import javax.persistence.*

@Entity
class User (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false)
    var username: String,
    @Column(nullable = false)
    var password: String,
    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.ROLE_USER
)