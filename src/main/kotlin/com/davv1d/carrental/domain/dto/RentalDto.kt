package com.davv1d.carrental.domain.dto

import java.time.LocalDateTime

class RentalDto(
        val id: Int = 0,
        val dateOfRent: LocalDateTime,
        val dateOfReturn: LocalDateTime,
        val vehicleDto: VehicleDto,
        val userDto: UserDto,
        val startLocationDto: LocationDto,
        val endLocationDto: LocationDto,
        val dateOfOrder: LocalDateTime
)