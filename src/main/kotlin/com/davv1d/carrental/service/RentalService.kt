package com.davv1d.carrental.service

import com.davv1d.carrental.domain.Rental
import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.RentalRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
                .flatMap { r -> vehicleService.getById(r.vehicle.id)
                            .flatMap { vehicle -> userService.getUserByName(r.user.username)
                                        .flatMap { user -> locationService.getById(r.startLocation.id)
                                                    .flatMap { startLocation -> locationService.getById(r.endLocation.id)
                                                                .map { endLocation -> with(r) { Rental(id, dateOfRent, dateOfReturn, vehicle, user, startLocation, endLocation) } }
                                                    }
                                        }
                            }
                }
                .flatMap(::secureSave)
    }

    fun getAll(): List<Rental> = rentalRepository.findAll()
    fun getByVehicleId(vehicleId: Int): List<Rental> = rentalRepository.findByVehicle_Id(vehicleId)
    fun getByStartLocationId(startLocationId: Int): List<Rental> = rentalRepository.findByStartLocation_Id(startLocationId)
    fun getByDateOfOrderIsAfter(dateOfOrder: LocalDateTime): List<Rental> = rentalRepository.findByDateOfOrderIsAfter(dateOfOrder)
    fun getByUsername(username: String): List<Rental> = rentalRepository.findByUser_Username(username)

    fun delete(rentalId: Int): Result<Rental> = generalService.simpleDelete(
            value = rentalId,
            findFunction = rentalRepository::findById,
            error = NotFoundElementException("RENTAL_WITH_THIS_ID_IS_NOT_EXIST"),
            deleteFunction = rentalRepository::deleteById
    )

}