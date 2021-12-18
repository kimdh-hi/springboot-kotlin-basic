package com.dhk.ecommerce.security.domain

import com.dhk.ecommerce.user.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl (
    var user: User,
    var enabled: Boolean = true
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority(user.role.toString()))

    override fun getPassword(): String = this.user.username

    override fun getUsername(): String = this.user.password

    override fun isAccountNonExpired(): Boolean = enabled

    override fun isAccountNonLocked(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean =enabled

    override fun isEnabled(): Boolean = enabled
}