package com.davv1d.carrental.domain

class VehicleParametersDto(
        val id: Int,
        val bodyType: String,
        val productionYear: Int,
        val fuelType: String,
        val power: Int,
        val vehicle: Vehicle
)