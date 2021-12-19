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
        // Http 요청에서 토큰추출
        val token = JwtTokenExtractor.extract(httpServletRequest)
        // 토큰이 존재하는 경우
        token?.let {
            if(jwtUtils.verifyToken(token)) { // 토큰검증
                val userId = jwtUtils.getClaim(token, "id") // 검증된 토큰에서 id값을 뽑아서 User를 조회
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