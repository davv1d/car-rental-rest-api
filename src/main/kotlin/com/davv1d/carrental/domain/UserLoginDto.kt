package com.davv1d.carrental.domain

import com.davv1d.carrental.constants.PASSWORD_NOT_BLANK
import com.davv1d.carrental.constants.USERNAME_NOT_BLANK
import javax.validation.constraints.NotBlank

class UserLoginDto(
        @get:NotBlank(message = USERNAME_NOT_BLANK)
        val username: String,
        @get:NotBlank(message = PASSWORD_NOT_BLANK)
        val password: String)