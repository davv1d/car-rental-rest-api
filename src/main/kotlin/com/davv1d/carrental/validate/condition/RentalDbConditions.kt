package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Rental
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.UserRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RentalDbConditions(
        private val vehicleRepository: VehicleRepository,
        private val locationRepository: LocationRepository,
        private val userRepository: UserRepository) {

    fun fetchRentalSaveConditions(): List<Condition<Rental>> {
        val condition1 = Condition<Rental>( "VEHICLE NOT AVAILABLE") { rental -> with(rental) { vehicleRepository.doesVehicleNotExistInAvailableVehicles(dateOfRent, dateOfReturn, startLocation.id, vehicle.id) } }
        val condition2 = Condition<Rental>( "START LOCATION NOT EXIST") { rental -> locationRepository.doesLocationNotExistById(rental.startLocation.id) }
        val condition3 = Condition<Rental>( "END LOCATION NOT EXIST") { rental -> locationRepository.doesLocationNotExistById(rental.endLocation.id) }
        val condition4 = Condition<Rental>( "USER NOT EXIST") { rental -> userRepository.doesNotExistByUsername(rental.user.username) }
        return listOf(condition1, condition2, condition3, condition4)
    }

    fun fetchRentalDateConditions(): List<Condition<Rental>> {
        val condition1 = Condition<Rental>("DATE OF RENT IS BEFORE CURRENT DATE + 1H") { rental -> with(rental) { dateOfRent.isBefore(LocalDateTime.now().plusHours(1)) } }
        val condition2 = Condition<Rental>("DATE OF RENT + 1H IS AFTER DATE OF RETURN") { rental -> with(rental) { dateOfRent.isAfter(dateOfReturn) } }
        return listOf(condition1, condition2)
    }
}