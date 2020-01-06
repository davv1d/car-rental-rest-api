package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.*
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun mapToUserDto(user: User): UserDto = with(user) { UserDto(username, email, role.name) }
    fun mapToUserDtoList(users: List<User>): List<UserDto> {
        return users.asSequence()
                .map(::mapToUserDto)
                .toList()
    }

    fun mapToUser(registrationDto: RegistrationDto): User = with(registrationDto) {
        User(username = username, password = password, email = email, role = Role(name = role))
    }

    fun mapToLogin(userLoginDto: UserLoginDto): UserLogin = with(userLoginDto) { UserLogin(username, password) }
}