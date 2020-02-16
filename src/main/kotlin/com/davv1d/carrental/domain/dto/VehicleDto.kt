package com.davv1d.carrental.domain.dto

import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class VehicleDto(
        val id: Int = 0,
        @get:NotBlank(message = "VEHICLE REGISTRATION MUST NOT BE BLANK")
        val registration: String = "",
        @get:NotBlank(message = "VEHICLE BRAND MUST NOT BE BLANK")
        val brand: String = "",
        @get:NotBlank(message = "VEHICLE MODEL MUST NOT BE BLANK")
        val model: String = "",
        val dailyFee: BigDecimal = BigDecimal.ZERO,
        @get:NotNull(message = "VEHICLE LOCATION MUST NOT BE NULL")
        val locationDto: LocationDto = LocationDto(),
        @get:NotBlank(message = "VEHICLE BODY TYPE MUST NOT BE BLANK")
        val bodyType: String = "",
        val productionYear: Int = 0,
        @get:NotBlank(message = "VEHICLE FUEL TYPE MUST NOT BE BLANK")
        val fuelType: String = "",
        val power: Int = 0
)