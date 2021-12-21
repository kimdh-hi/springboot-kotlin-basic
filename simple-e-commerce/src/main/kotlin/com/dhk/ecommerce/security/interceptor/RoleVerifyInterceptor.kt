package com.dhk.ecommerce.security.interceptor

import com.dhk.ecommerce.security.utils.JwtTokenExtractor
import com.dhk.ecommerce.security.utils.JwtTokenProvider
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RoleVerifyInterceptor(private val jwtTokenProvider: JwtTokenProvider): HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if(isPreRequest(request)) return true

        val token = JwtTokenExtractor.extract(request)
        token?.let {
            if(jwtTokenProvider.verifySellerToken(it)) return true
        }

        throw AuthenticationException("인증실패")
    }

    private fun isPreRequest(request: HttpServletRequest): Boolean {
        return request.method.equals(HttpMethod.OPTIONS)
    }
}