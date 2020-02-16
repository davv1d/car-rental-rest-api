package com.davv1d.carrental.facade

import com.davv1d.carrental.domain.dto.RegistrationDto
import com.davv1d.carrental.domain.dto.UserDto
import com.davv1d.carrental.domain.dto.UserLoginDto
import com.davv1d.carrental.mapper.UserMapper
import com.davv1d.carrental.security.JwtProvider
import com.davv1d.carrental.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationFacade(
        private val userService: UserService,
        private val userMapper: UserMapper,
        private val authenticationManager: AuthenticationManager,
        private val jwtProvider: JwtProvider)
{
    private val logger: Logger = LoggerFactory.getLogger(AuthenticationFacade::class.java)

    fun userRegistration(registrationDto: RegistrationDto): UserDto {
        return userService.save(userMapper.mapToUser(registrationDto))
                .effect(onSuccess = userMapper::mapToUserDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { UserDto() })
    }

    fun userLogin(userLoginDto: UserLoginDto): String {
        val userLogin = userMapper.mapToLogin(userLoginDto)
        val authenticate = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        userLogin.username,
                        userLogin.password
                )
        )
        SecurityContextHolder.getContext().authentication = authenticate
        return jwtProvider.generateJwtToken(authenticate)
    }

}