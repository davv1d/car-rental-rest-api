package com.davv1d.carrental.domain.dto

import java.time.LocalDateTime

class VehicleLocationDto(
        val id: Int = 0,
        val date: LocalDateTime = LocalDateTime.now(),
        val locationDto: LocationDto = LocationDto(),
        val vehicleDto: VehicleDto
)