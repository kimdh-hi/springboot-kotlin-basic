package com.dhk.ecommerce.security.interceptor

import org.springframework.http.HttpMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class PatternMatcherInterceptor(
    var targetInterceptor: HandlerInterceptor, // 적용될 인터셉터
) : HandlerInterceptor {

    var addPathPatterns: MutableMap<String, HttpMethod> = mutableMapOf() // 인터셉터를 적용조건 <요청URI, 요청메서드>

    fun addPathPatterns(uri: String, httpMethod: HttpMethod) = this.addPathPatterns.put(uri, httpMethod)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val method = request.method
        val requestURI = request.requestURI

        // URI와 요청메서드가 일치하는 경우 targetInterceptor 를 통하도록 처리
        for (uri in addPathPatterns.keys) {
            if (requestURI.equals(uri) && addPathPatterns[uri].toString() == method) {
                return targetInterceptor.preHandle(request,response, handler)
            }
        }

        return true
    }
}