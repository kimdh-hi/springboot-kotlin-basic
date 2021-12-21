package com.dhk.ecommerce.security.interceptor

import org.springframework.http.HttpMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UriMatcherInterceptor(
    var targetInterceptor: HandlerInterceptor,
) : HandlerInterceptor {

    var addPathPatterns: MutableMap<String, HttpMethod> = mutableMapOf()

    fun addPathPatterns(uri: String, httpMethod: HttpMethod) = this.addPathPatterns.put(uri, httpMethod)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val method = request.method
        val requestURI = request.requestURI

        for (uri in addPathPatterns.keys) {
            if (requestURI.equals(uri) && addPathPatterns[uri].toString() == method) {
                return targetInterceptor.preHandle(request,response, handler)
            }
        }

        return true
    }
}