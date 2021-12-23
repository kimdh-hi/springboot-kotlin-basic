package com.dhk.ecommerce.common.config

import com.dhk.ecommerce.security.AuthenticatedUserArgumentResolver
import com.dhk.ecommerce.security.interceptor.RoleVerifyInterceptor
import com.dhk.ecommerce.security.interceptor.TokenVerifyInterceptor
import com.dhk.ecommerce.security.interceptor.PatternMatcherInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val tokenVerifyInterceptor: TokenVerifyInterceptor,
    private val roleVerifyInterceptor: RoleVerifyInterceptor,
    private val authenticatedUserArgumentResolver: AuthenticatedUserArgumentResolver) : WebMvcConfigurer{

    override fun addInterceptors(registry: InterceptorRegistry) {
        val patternMatcherInterceptor1 = PatternMatcherInterceptor(roleVerifyInterceptor) // 판매자 권한 검증을 위한 인터셉터
        patternMatcherInterceptor1.addPathPatterns("/test/auth-test/seller", HttpMethod.GET)
        patternMatcherInterceptor1.addPathPatterns("/items", HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)

        val patternMatcherInterceptor2 = PatternMatcherInterceptor(tokenVerifyInterceptor) // 일반 검증 인터셉터
        patternMatcherInterceptor2.addPathPatterns("/test/auth-test", HttpMethod.GET)
        patternMatcherInterceptor2.addPathPatterns("/orders", HttpMethod.POST)

        registry.addInterceptor(patternMatcherInterceptor1)
            .addPathPatterns("/**")
        registry.addInterceptor(patternMatcherInterceptor2)
            .addPathPatterns("/**")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticatedUserArgumentResolver)
    }
}

//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(tokenVerifyInterceptor)
//            .addPathPatterns("/**")
//            .excludePathPatterns("/users/signup", "/users/signin")
//            .excludePathPatterns("/items")
//    }