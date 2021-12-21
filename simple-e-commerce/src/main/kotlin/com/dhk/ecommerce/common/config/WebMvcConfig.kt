package com.dhk.ecommerce.common.config

import com.dhk.ecommerce.security.AuthenticatedUserArgumentResolver
import com.dhk.ecommerce.security.interceptor.RoleVerifyInterceptor
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
    private val roleVerifyInterceptor: RoleVerifyInterceptor,
    private val authenticatedUserArgumentResolver: AuthenticatedUserArgumentResolver) : WebMvcConfigurer{

//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(tokenVerifyInterceptor)
//            .addPathPatterns("/**")
//            .excludePathPatterns("/users/signup", "/users/signin")
//            .excludePathPatterns("/items")
//    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        val uriMatcherInterceptor1 = UriMatcherInterceptor(roleVerifyInterceptor)
        uriMatcherInterceptor1.addPathPatterns("/test/auth-test/seller", HttpMethod.GET)

        val uriMatcherInterceptor2 = UriMatcherInterceptor(tokenVerifyInterceptor)
        uriMatcherInterceptor2.addPathPatterns("/items", HttpMethod.POST)
        uriMatcherInterceptor2.addPathPatterns("/test/auth-test", HttpMethod.GET)

        registry.addInterceptor(uriMatcherInterceptor1)
            .order(1)
        registry.addInterceptor(uriMatcherInterceptor2)
            .order(2)
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticatedUserArgumentResolver)
    }
}