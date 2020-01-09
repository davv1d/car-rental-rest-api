package com.davv1d.carrental.security

import com.davv1d.carrental.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(private val user: User) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return user.role.privileges
                .map { SimpleGrantedAuthority(it.name) }
                .toList()
    }

    override fun isEnabled(): Boolean = true
    override fun getUsername(): String = user.username
    override fun isCredentialsNonExpired(): Boolean = true
    override fun getPassword(): String = user.password
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
}