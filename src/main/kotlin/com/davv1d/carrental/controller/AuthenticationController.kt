package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.dto.RegistrationDto
import com.davv1d.carrental.domain.dto.UserDto
import com.davv1d.carrental.domain.dto.UserLoginDto
import com.davv1d.carrental.facade.AuthenticationFacade
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AuthenticationController(private val authenticationFacade: AuthenticationFacade) {

    @PostMapping(value = ["/user-registration"])
    fun userRegistration(@RequestBody @Valid registrationDto: RegistrationDto): UserDto {
        return authenticationFacade.userRegistration(registrationDto)
    }

    @PostMapping(value = ["/user-login"])
    fun userLogin(@RequestBody @Valid userLoginDto: UserLoginDto): String {
        return authenticationFacade.userLogin(userLoginDto)
    }

}