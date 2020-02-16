package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.domain.dto.VehicleDto
import org.springframework.stereotype.Component

@Component
class VehicleMapper(private val locationMapper: LocationMapper) {
    fun mapToVehicleDto(vehicle: Vehicle): VehicleDto = with(vehicle) {
        VehicleDto(id, registration, brand, model, dailyFee, locationMapper.mapToLocationDto(location), bodyType, productionYear, fuelType, power)
    }

    fun mapToVehicle(vehicleDto: VehicleDto): Vehicle = with(vehicleDto) {
        Vehicle(id, registration, brand, model, dailyFee, locationMapper.mapToLocation(locationDto), bodyType, productionYear, fuelType, power)
    }

    fun mapToVehicleDtoList(vehicles: List<Vehicle>): List<VehicleDto> {
        return vehicles.asSequence()
                .map { mapToVehicleDto(it) }
                .toList()
    }
}