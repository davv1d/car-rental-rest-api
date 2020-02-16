package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.User
import com.davv1d.carrental.domain.UserLogin
import com.davv1d.carrental.domain.dto.RegistrationDto
import com.davv1d.carrental.domain.dto.UserDto
import com.davv1d.carrental.domain.dto.UserLoginDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserMapper(private val roleMapper: RoleMapper, val passwordEncoder: PasswordEncoder) {
    fun mapToUserDto(user: User): UserDto = with(user) { UserDto(id, username, email, role.name) }
    fun mapToUserDtoList(users: List<User>): List<UserDto> {
        return users.asSequence()
                .map(::mapToUserDto)
                .toList()
    }

    fun mapToUser(registrationDto: RegistrationDto): User = with(registrationDto) {
        User(username = username, password = passwordEncoder.encode(password), email = email, role = Role(name = role))
    }

    fun mapToLogin(userLoginDto: UserLoginDto): UserLogin = with(userLoginDto) { UserLogin(username, password) }
}