package com.dhk.ecommerce.security

import com.dhk.ecommerce.security.utils.JwtTokenExtractor
import com.dhk.ecommerce.security.utils.JwtTokenProvider
import com.dhk.ecommerce.user.repository.UserRepository
import org.springframework.core.MethodParameter
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest

@Component
class AuthenticatedUserArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthenticatedUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?
    ): Any? {
        val httpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java) as HttpServletRequest
        val token: String? = JwtTokenExtractor.extract(httpServletRequest)
        token?.let {
            if (jwtTokenProvider.verifyToken(it)) {
                val userId = jwtTokenProvider.getUserIdFromClaims(it)
                return userRepository.findByIdOrNull(userId)
            }
        } ?: throw AuthenticationException("인증실패")
        return null
    }
}