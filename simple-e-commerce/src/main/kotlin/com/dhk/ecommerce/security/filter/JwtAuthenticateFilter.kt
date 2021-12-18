package com.dhk.ecommerce.security.filter

import com.dhk.ecommerce.security.utils.JwtTokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticateFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    companion object{
        const val AUTHORIZATION_HEADER = "Authorization"
        const val BEARER_PREFIX = "Bearer "

        val WHITE_LIST = listOf(
            "/signup", "/login"
        )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val requestURI = request.requestURI
        if (WHITE_LIST.contains(requestURI)) return filterChain.doFilter(request, response)

        val authHeader: String? = request.getHeader(AUTHORIZATION_HEADER)

        authHeader?.let {
            if (authHeader.startsWith(BEARER_PREFIX)) {
                val token = authHeader.substring(BEARER_PREFIX.length)
                if (jwtTokenProvider.verifyToken(token)) {
                    val userDetails = jwtTokenProvider.getUserDetails(token)
                    val authentication =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        }

        return filterChain.doFilter(request, response)
    }
}