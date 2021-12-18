package com.dhk.ecommerce.security.utils

import com.dhk.ecommerce.security.service.UserDetailsServiceImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(private val userDetailsServiceImpl: UserDetailsServiceImpl) {

    @Value("\$(jwt.token}")
    lateinit var JWT_TOKEN_SECRET: String

    val SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256
    val EXP_TIME = 1000 * 60 * 10


    fun generateToken(username: String) {
        val now = Date()
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(Date(System.currentTimeMillis() + EXP_TIME))
            .signWith(SIGNATURE_ALGORITHM, JWT_TOKEN_SECRET)
            .compact()
    }

    fun getUserDetails(token: String): UserDetails {
        val username = getSubject(token)
        return userDetailsServiceImpl.loadUserByUsername(username)
    }

    private fun getSubject(token: String): String {
        val claims = getAllClaims(token)
        return claims.subject
    }

    fun verifyToken(token: String): Boolean {
        val claims = getAllClaims(token)
        val expiration = claims.expiration

        return expiration.before(Date())
    }

    private fun getAllClaims(token: String) : Claims {
        return Jwts.parser()
            .setSigningKey(JWT_TOKEN_SECRET)
            .parseClaimsJwt(token)
            .body
    }
}