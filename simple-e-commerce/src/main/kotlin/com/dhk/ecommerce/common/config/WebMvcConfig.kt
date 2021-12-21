package com.dhk.ecommerce.common.config

import com.dhk.ecommerce.security.AuthenticatedUserArgumentResolver
import com.dhk.ecommerce.security.interceptor.TokenVerifyInterceptor
import com.dhk.ecommerce.security.interceptor.UriMatcherInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val tokenVerifyInterceptor: TokenVerifyInterceptor,
    private val authenticatedUserArgumentResolver: AuthenticatedUserArgumentResolver) : WebMvcConfigurer{

//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(tokenVerifyInterceptor)
//            .addPathPatterns("/**")
//            .excludePathPatterns("/users/signup", "/users/signin")
//            .excludePathPatterns("/items")
//    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        val uriMatcherInterceptor = UriMatcherInterceptor(tokenVerifyInterceptor)
        uriMatcherInterceptor.addPathPatterns("/items", HttpMethod.POST)

        registry.addInterceptor(uriMatcherInterceptor)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticatedUserArgumentResolver)
    }
}