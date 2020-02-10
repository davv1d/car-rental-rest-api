package com.davv1d.carrental.domain

import javax.validation.Valid
import javax.validation.constraints.NotBlank

class RoleDto(
        val id: Int = 0,
        @get:NotBlank(message = "ROLE NAME MUST NOT BE BLANK")
        val roleName: String = "",
        @get:Valid
        val privileges: List<PrivilegeDto> = emptyList())