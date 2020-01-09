package com.davv1d.carrental.security

import com.davv1d.carrental.security.JwtProperties.STRING_HEADER
import com.davv1d.carrental.security.JwtProperties.TOKEN_TYPE
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthTokenFilter(val tokenProvider: JwtProvider, val userPrincipalDetailService: UserPrincipalDetailService) : OncePerRequestFilter() {
    private val LOGGER = LoggerFactory.getLogger(JwtAuthTokenFilter::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val jwtToken = getToken(request)
            if (jwtToken != null && tokenProvider.validateJwtToken(jwtToken)) {
                val userNameFromJwtToken: String = tokenProvider.getUsernameFromJwtToken(jwtToken)
                val userDetails = userPrincipalDetailService.loadUserByUsername(userNameFromJwtToken)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            LOGGER.error("Can not set user authentication -> Message: ", e)
        }
        filterChain.doFilter(request, response)
    }


    private fun getToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader(STRING_HEADER)
        return if (authHeader != null && authHeader.startsWith(TOKEN_TYPE)) {
            authHeader.replace(TOKEN_TYPE, "")
        } else null
    }
}