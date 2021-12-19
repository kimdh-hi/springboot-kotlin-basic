package com.pure.login.auth

import com.pure.login.domain.User
import com.pure.login.repository.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.servlet.http.HttpServletRequest

@Component
class LoginUserArgumentResolver(
    private val jwtUtils: JwtUtils,
    private val userRepository: UserRepository
): HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User? {

        val httpServletRequest: HttpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java) as HttpServletRequest
        val token = JwtTokenExtractor.extract(httpServletRequest)
        token?.let {
            if(jwtUtils.verifyToken(token)) {
                val userId = jwtUtils.getClaim(token, "id")
                return userRepository.findByIdOrNull(userId as Long)
            }
        }

        return null
    }

    // 토큰에서 user pk 추출
    private fun getUserIdFromToken(token: String): Long? {
        return jwtUtils.getClaim(token, "id") as Long?
    }
}