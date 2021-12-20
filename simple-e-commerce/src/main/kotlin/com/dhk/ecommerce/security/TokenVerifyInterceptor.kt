package com.dhk.ecommerce.security

import com.dhk.ecommerce.security.utils.JwtTokenExtractor
import com.dhk.ecommerce.security.utils.JwtTokenProvider
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenVerifyInterceptor(private val jwtTokenProvider: JwtTokenProvider) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        isPreRequest(request)

        val token: String? = JwtTokenExtractor.extract(request)
        token?.let {
            jwtTokenProvider.verifyToken(it)
        } ?: throw AuthenticationException("인증실패")

        return true
    }

    private fun isPreRequest(request: HttpServletRequest): Boolean {
        return request.method.equals(HttpMethod.OPTIONS)
    }
}