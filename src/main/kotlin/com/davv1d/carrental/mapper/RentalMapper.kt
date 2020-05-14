package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.*
import com.davv1d.carrental.domain.dto.RentalDto
import com.davv1d.carrental.domain.dto.SaveRentalDto
import com.davv1d.carrental.domain.dto.UpdateRentalVehicleDto
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RentalMapper(
        private val vehicleMapper: VehicleMapper,
        private val locationMapper: LocationMapper,
        private val userMapper: UserMapper) {

    fun mapToRentalDto(rental: Rental): RentalDto = with(rental) {
        RentalDto(id, dateOfRent, dateOfReturn, vehicleMapper.mapToVehicleDto(vehicle), userMapper.mapToUserDto(user), locationMapper.mapToLocationDto(startLocation), locationMapper.mapToLocationDto(endLocation), dateOfOrder)
    }

    fun mapToRentalDtoList(rentals: List<Rental>): List<RentalDto> = rentals.map(::mapToRentalDto)

    fun mapToRental(saveRentalDto: SaveRentalDto): Rental = with(saveRentalDto) {
        val vehicle = Vehicle(id = vehicleId)
        val user = User(username = username, email = "", password = "", role = Role(name = ""))
        val startLocation = Location(id = startLocationId, city = "", street = "")
        val endLocation = Location(id = endLocationId, city = "", street = "")
        Rental(0, dateOfRent, dateOfReturn, vehicle, user, startLocation, endLocation)
    }

    fun mapToRental(updateRentalVehicleDto: UpdateRentalVehicleDto): Rental = with(updateRentalVehicleDto) {
        val vehicle = Vehicle(id = vehicleId)
        val user = User(username = "", email = "", password = "", role = Role(name = ""))
        val startLocation = Location(city = "", street = "")
        val endLocation = Location(city = "", street = "")
        Rental(rentalId, LocalDateTime.now(), LocalDateTime.now(), vehicle, user, startLocation, endLocation)
    }
}