package com.davv1d.carrental.domain

import com.davv1d.carrental.constants.INCORRECT_EMAIL
import com.davv1d.carrental.constants.PASSWORD_NOT_BLANK
import com.davv1d.carrental.constants.ROLE_NOT_BLANK
import com.davv1d.carrental.constants.USERNAME_NOT_BLANK
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

class RegistrationDto(@get:NotBlank(message = USERNAME_NOT_BLANK)
                      val username: String,
                      @get:NotBlank(message = PASSWORD_NOT_BLANK)
                      val password: String,
                      @get:Email(message = INCORRECT_EMAIL)
                      val email: String,
                      @get:NotBlank(message = ROLE_NOT_BLANK)
                      val role: String)