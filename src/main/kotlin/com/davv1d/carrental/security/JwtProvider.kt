package com.davv1d.carrental.security

import com.davv1d.carrental.security.JwtProperties.EXPIRATION
import com.davv1d.carrental.security.JwtProperties.SECRET
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtProvider {
    private val logger: Logger = LoggerFactory.getLogger(JwtProvider::class.java)

    fun generateJwtToken(authentication: Authentication): String {
        val principal: UserPrincipal = authentication.principal as UserPrincipal
        return Jwts.builder()
                .setSubject(principal.username)
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact()
    }

    fun getUsernameFromJwtToken(token: String): String {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .body.subject
    }

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(authToken)
            return true
        } catch (e: ExpiredJwtException) {
            logger.error("Expired JWT token -> Message: {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT Token -> Message: {}", e.message)
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token -> Message: {}", e.message)
        } catch (e: SignatureException) {
            logger.error("Invalid JWT signature -> Message: {} ", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("Jwt claims string is empty -> Message: {}", e.message)
        }
        return false
    }
}