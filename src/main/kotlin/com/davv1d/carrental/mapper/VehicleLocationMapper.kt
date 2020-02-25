package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.VehicleLocation
import com.davv1d.carrental.domain.dto.VehicleLocationDto
import org.springframework.stereotype.Component

@Component
class VehicleLocationMapper(private val locationMapper: LocationMapper, private val vehicleMapper: VehicleMapper) {
    fun mapToVehicleLocationDto(vehicleLocation: VehicleLocation): VehicleLocationDto = with(vehicleLocation) {
        VehicleLocationDto(id, date, locationMapper.mapToLocationDto(location), vehicleMapper.mapToVehicleDto(vehicle))
    }

    fun mapToVehicleLocationDtoList(vehicleLocations: List<VehicleLocation>): List<VehicleLocationDto> = vehicleLocations.map { mapToVehicleLocationDto(it) }

    fun mapToVehicleLocation(vehicleLocationDto: VehicleLocationDto): VehicleLocation = with(vehicleLocationDto) {
        VehicleLocation(id, date, locationMapper.mapToLocation(locationDto), vehicleMapper.mapToVehicle(vehicleDto))
    }
}