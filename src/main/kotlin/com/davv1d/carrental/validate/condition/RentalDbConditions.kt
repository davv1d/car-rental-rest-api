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
        val userCondition = Condition<Rental>("USER NOT EXIST") { rental -> userRepository.existsByUsernameAmongActiveUsers(rental.user.username) }
        val vehicleCondition = Condition<Rental>("VEHICLE NOT AVAILABLE") { rental -> with(rental) { vehicleRepository.doesVehicleExistInAvailableVehicles(dateOfRent, dateOfReturn, startLocation.id, vehicle.id) } }
        val startLocationCondition = Condition<Rental>("START LOCATION NOT EXIST") { rental -> locationRepository.existsById(rental.startLocation.id) }
        val endLocationCondition = Condition<Rental>("END LOCATION NOT EXIST") { rental -> locationRepository.existsById(rental.endLocation.id) }
        return listOf(vehicleCondition, startLocationCondition, endLocationCondition, userCondition)
    }

    fun fetchRentalDateConditions(): List<Condition<Rental>> {
        val dateCondition1 = Condition<Rental>("DATE OF RENT IS BEFORE CURRENT DATE + 1H") { rental -> with(rental) { dateOfRent.isAfter(LocalDateTime.now().plusHours(1)) } }
        val dateCondition2 = Condition<Rental>("DATE OF RENT + 1H IS AFTER DATE OF RETURN") { rental -> with(rental) { dateOfRent.isBefore(dateOfReturn.minusHours(1)) } }
        return listOf(dateCondition1, dateCondition2)
    }

    fun fetchRentalChangeVehicleConditions(): List<Condition<Rental>> {
        val rentalWithoutVehicleCondition = Condition<Rental>("RENTAL DOES NOT EXIST") { rental -> with(rental) { rentalRepository.doesRentalExist(id, dateOfRent, dateOfReturn, startLocation.id, endLocation.id, user.username) } }
        val vehicleCondition = Condition<Rental>("VEHICLE NOT AVAILABLE") { rental -> with(rental) { vehicleRepository.doesVehicleExistInAvailableVehicles(dateOfRent, dateOfReturn, startLocation.id, vehicle.id) } }
        return listOf(rentalWithoutVehicleCondition, vehicleCondition)
    }
}