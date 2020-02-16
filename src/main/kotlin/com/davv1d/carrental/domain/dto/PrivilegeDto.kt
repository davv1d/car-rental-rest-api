package com.davv1d.carrental.domain.dto

import javax.validation.constraints.NotBlank

class PrivilegeDto(
        @get:NotBlank(message = "PRIVILEGE NAME MUST NOT BE BLANK")
        val name: String = "")