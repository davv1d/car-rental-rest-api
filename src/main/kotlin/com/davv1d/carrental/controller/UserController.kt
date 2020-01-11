package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.UserDto
import com.davv1d.carrental.mapper.UserMapper
import com.davv1d.carrental.security.JwtProvider
import com.davv1d.carrental.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class UserController(private val userService: UserService, private val userMapper: UserMapper) {
    private val logger: Logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping(value = ["/logged-user"])
    fun getLoggedUser(principal: Principal): UserDto {
        return userService.getUserByName(principal.name)
                .effect(onSuccess = userMapper::mapToUserDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { UserDto() })
    }

    @GetMapping(value = ["/user"], params = ["name"])
    fun getUserByName(name: String): UserDto {
        return userService.getUserByName(name)
                .effect(onSuccess = userMapper::mapToUserDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { UserDto() })
    }

    @GetMapping(value = ["/user"], params = ["email"])
    fun getUserByEmail(email: String): UserDto {
        return userService.getUserByEmail(email)
                .effect(onSuccess = userMapper::mapToUserDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { UserDto() })
    }

    @GetMapping(value = ["/user"])
    fun getAllUser(): List<UserDto> {
        return userMapper.mapToUserDtoList(userService.getAllUsers())
    }

    @DeleteMapping(value = ["/user"], params = ["name"])
    fun deleteUserByName(name: String) {
        userService.deleteUserByUsername(name)
                .effect(onSuccess = {}, onFailure = { exception -> logger.error(exception.message) }, onEmpty = {})
    }

    @PutMapping(value = ["/user"], params = ["email"])
    fun changeEmail(email: String, principal: Principal): UserDto {
        return userService.changeEmail(username = principal.name, email = email)
                .effect(onSuccess = userMapper::mapToUserDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { UserDto() })
    }
}