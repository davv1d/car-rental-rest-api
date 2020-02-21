package com.davv1d.carrental.domain.dto

import com.davv1d.carrental.constants.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class CustomerRegistrationDto(@get:NotBlank(message = USERNAME_NOT_BLANK)
                              val username: String,
                              @get:NotBlank(message = PASSWORD_NOT_BLANK)
                              val password: String,
                              @get:Email(message = INCORRECT_EMAIL)
                              val email: String)