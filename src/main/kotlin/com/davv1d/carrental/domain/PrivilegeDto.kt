package com.davv1d.carrental.domain

import javax.validation.constraints.NotBlank

class PrivilegeDto(
        @get:NotBlank(message = "PRIVILEGE NAME MUST NOT BE BLANK")
        val name: String = "") {
}