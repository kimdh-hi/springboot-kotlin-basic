package com.comments.article.domain

import com.comments.user.domain.Account
import javax.persistence.*

@Entity
class Article (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var title: String,
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    var account: Account
)