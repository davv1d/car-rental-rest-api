package com.davv1d.carrental.domain

import javax.validation.constraints.NotBlank

class LocationDto(
        val id: Int = 0,
        @get:NotBlank(message = "LOCATION CITY MUST NOT BE BLANK")
        val city: String = "",
        @get:NotBlank(message = "LOCATION STREET MUST NOT BE BLANK")
        val street: String = "")
