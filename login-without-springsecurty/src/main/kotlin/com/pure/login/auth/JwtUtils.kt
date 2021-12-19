package com.pure.login.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.*

@Component
class JwtUtils {

    val SECRET = "secret"
    val EXP_TIME = 1000 * 60 * 10

    fun generateToken(userId: Long, username: String): String {
        return Jwts.builder()
            .setSubject(username)
            .claim("userId", userId)
            .setExpiration(Date(System.currentTimeMillis() + EXP_TIME))
            .signWith(SignatureAlgorithm.HS256, SECRET)
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

    fun getClaim(token: String, key: String ): Any? {
        val claims: Claims = getAllClaims(token)
        return claims[key]
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(SECRET)
            .parseClaimsJwt(token).body
    }
}