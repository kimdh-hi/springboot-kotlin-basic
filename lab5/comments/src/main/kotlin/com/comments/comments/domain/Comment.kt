package com.comments.comments.domain

import com.comments.user.domain.Account
import lombok.Builder
import javax.persistence.*

class Comment (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    var account: Account
)