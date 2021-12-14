package com.example.lab2.security

import com.example.lab2.domain.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(val member: Member) : UserDetails {

    var enabled: Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = AuthorityUtils.createAuthorityList()

    override fun getPassword(): String = member.password

    override fun getUsername(): String = member.password

    override fun isAccountNonExpired(): Boolean = enabled

    override fun isAccountNonLocked(): Boolean = enabled

    override fun isCredentialsNonExpired(): Boolean = enabled

    override fun isEnabled(): Boolean = enabled
}