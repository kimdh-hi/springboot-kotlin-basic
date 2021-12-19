package com.pure.login.auth

import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenVerifyInterceptor(
    private val jwtUtils: JwtUtils
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val method = request.method
        if (method.equals(HttpMethod.OPTIONS)) return true

        val token: String? = JwtTokenExtractor.extract(request)
        token?.let {
            jwtUtils.verifyToken(it)
        }

        return true
    }
}