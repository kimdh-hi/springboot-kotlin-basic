package com.dhk.ecommerce.security.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {

    @Value("\$(jwt.token}")
    lateinit var JWT_TOKEN_SECRET: String
    val EXP_TIME = 1000 * 60 * 10


    fun generateToken(userId: Long, username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("userId", userId)
            .setExpiration(Date(System.currentTimeMillis() + EXP_TIME))
            .signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SECRET)
            .compact()
    }

    fun verifyToken(token: String): Boolean {
        return try {
            val claims = getAllClaims(token)
            val expiration = claims.expiration
            expiration.after(Date())
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getUserIdFromClaims(token: String): Long {
        val claims: Claims = getAllClaims(token)
        when (val userId = claims["userId"]) {
            is Number -> return userId.toLong()
            else -> throw IllegalArgumentException("타입오류")
        }
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(JWT_TOKEN_SECRET)
            .parseClaimsJws(token).body
    }
}