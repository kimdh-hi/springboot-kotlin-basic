package com.comments.comment.domain

import com.comments.user.domain.Account
import javax.persistence.*

@Entity
class Comment (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var content: String,

    var isDeleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    var parent: Comment?,

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    var child: MutableList<Comment>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    var account: Account
)