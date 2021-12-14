package com.example.lab2.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class Timestamped {

    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
}