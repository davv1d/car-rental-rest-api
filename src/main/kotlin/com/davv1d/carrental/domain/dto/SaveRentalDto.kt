package com.davv1d.carrental.domain.dto

import java.time.LocalDateTime

class SaveRentalDto(
        val dateOfRent: LocalDateTime,
        val dateOfReturn: LocalDateTime,
        val vehicleId: Int,
        val username: String,
        val startLocationId: Int,
        val endLocationId: Int
)