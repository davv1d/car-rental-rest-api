package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.RegistrationDto
import com.davv1d.carrental.domain.UserDto
import com.davv1d.carrental.mapper.UserMapper
import com.davv1d.carrental.repository.RoleRepository
import com.davv1d.carrental.repository.UserRepository
import com.davv1d.carrental.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class TestController(@Autowired val userRepository: UserRepository, @Autowired val roleRepository: RoleRepository, @Autowired val userService: UserService) {

    private val logger: Logger = LoggerFactory.getLogger(TestController::class.java)

    @Autowired
    lateinit var userMapper: UserMapper
//    @PostConstruct
//    fun roleCreator() {
//        val role = Role(name = "admin")
//        println("role save")
//        roleRepository.save(role)
//    }

    @PostMapping("/test")
    fun test(@RequestBody @Valid registrationDto: RegistrationDto): UserDto {
        return userService.save(userMapper.mapToUser(registrationDto))
                .effect(onSuccess = userMapper::mapToUserDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { UserDto() })

    }
}