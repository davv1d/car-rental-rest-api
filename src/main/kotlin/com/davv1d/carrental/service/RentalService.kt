package com.davv1d.carrental.service

import com.davv1d.carrental.domain.Rental
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.RentalRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service

@Service
class RentalService(
        private val rentalRepository: RentalRepository,
        private val rentalSaveDateValidator: ConditionValidator<Rental>,
        private val rentalSaveValidator: ConditionValidator<Rental>,
        private val generalService: GeneralService,
        private val locationService: LocationService,
        private val userService: UserService,
        private val vehicleService: VehicleService) {

    fun secureSave(rental: Rental): Result<Rental> = generalService.secureSave(value = rental, function = rentalRepository::save)

    fun save(rental: Rental): Result<Rental> {
        return rentalSaveDateValidator.dbValidate(rental)
                .flatMap { rentalSaveValidator.dbValidate(it) }
                .flatMap { rental -> vehicleService.getById(rental.vehicle.id)
                        .flatMap { vehicle -> userService.getUserByName(rental.user.username)
                                .flatMap { user -> locationService.getById(rental.startLocation.id)
                                        .flatMap { startLocation -> locationService.getById(rental.endLocation.id)
                                                .map { endLocation -> with(rental) {Rental(id, dateOfRent, dateOfReturn, vehicle, user, startLocation, endLocation)} }}}}}
                .flatMap(::secureSave)
    }
}