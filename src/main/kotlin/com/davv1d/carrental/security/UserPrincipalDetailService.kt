package com.davv1d.carrental.security

import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.service.UserService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserPrincipalDetailService(private val userService: UserService) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.getUserByName(username).getOrElse { throw NotFoundElementException("user is not found security") }
        return UserPrincipal(user)
    }
}