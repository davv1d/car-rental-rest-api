package com.davv1d.carrental.domain

import java.math.BigDecimal

class VehicleDto(
        val id: Int = 0,
        val registration: String,
        val brand: String,
        val model: String,
        val dailyFee: BigDecimal,
        val locationDto: LocationDto,
        val bodyType: String,
        val productionYear: Int,
        val fuelType: String,
        val power: Int
)