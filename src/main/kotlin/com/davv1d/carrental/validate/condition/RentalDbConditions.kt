package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Rental
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.RentalRepository
import com.davv1d.carrental.repository.UserRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RentalDbConditions(
        private val vehicleRepository: VehicleRepository,
        private val locationRepository: LocationRepository,
        private val userRepository: UserRepository,
        private val rentalRepository: RentalRepository) {

    fun fetchRentalSaveConditions(): List<Condition<Rental>> {
        val (vehicleConditions, startLocationConditions, endLocationConditions) = fetchVehicleAndLocationConditions()
        val userConditions = Condition<Rental>("USER NOT EXIST") { rental -> userRepository.existsByUsernameAmongActiveUsers(rental.user.username) }
        return listOf(vehicleConditions, startLocationConditions, endLocationConditions, userConditions)
    }

    fun fetchRentalDateConditions(): List<Condition<Rental>> {
        val condition1 = Condition<Rental>("DATE OF RENT IS BEFORE CURRENT DATE + 1H") { rental -> with(rental) { dateOfRent.isAfter(LocalDateTime.now().plusHours(1)) } }
        val condition2 = Condition<Rental>("DATE OF RENT + 1H IS AFTER DATE OF RETURN") { rental -> with(rental) { dateOfRent.isBefore(dateOfReturn.minusHours(1)) } }
        return listOf(condition1, condition2)
    }

    fun fetchRentalUpdateConditions(): List<Condition<Rental>> {
        val condition1 = Condition<Rental>("RENTAL ID DOES NOT EXIST") { rental -> rentalRepository.existsById(rental.id) }
        val vehicleConditions = Condition<Rental>("VEHICLE NOT AVAILABLE") { rental -> with(rental) { vehicleRepository.doesVehicleExistInAvailableVehicles(dateOfRent, dateOfReturn, startLocation.id, vehicle.id) } }
        return listOf(condition1, vehicleConditions)
    }

    private fun fetchVehicleAndLocationConditions(): Triple<Condition<Rental>, Condition<Rental>, Condition<Rental>> {
        val vehicleConditions = Condition<Rental>("VEHICLE NOT AVAILABLE") { rental -> with(rental) { vehicleRepository.doesVehicleExistInAvailableVehicles(dateOfRent, dateOfReturn, startLocation.id, vehicle.id) } }
        val startLocationConditions = Condition<Rental>("START LOCATION NOT EXIST") { rental -> locationRepository.existsById(rental.startLocation.id) }
        val endLocationConditions = Condition<Rental>("END LOCATION NOT EXIST") { rental -> locationRepository.existsById(rental.endLocation.id) }
        return Triple(vehicleConditions, startLocationConditions, endLocationConditions)
    }
}