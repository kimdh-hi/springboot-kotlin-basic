package com.pure.login.config

import com.pure.login.auth.LoginUserArgumentResolver
import com.pure.login.auth.TokenVerifyInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val tokenVerifyInterceptor: TokenVerifyInterceptor,
    private val loginUserArgumentResolver: LoginUserArgumentResolver
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(tokenVerifyInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/user/signup", "/user/signin")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserArgumentResolver)
    }
}