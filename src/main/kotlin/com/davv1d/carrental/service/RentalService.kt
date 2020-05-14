package com.davv1d.carrental.service

import com.davv1d.carrental.domain.*
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
        private val rentalChangeVehicleValidator: ConditionValidator<Rental>,
        private val locationService: LocationService,
        private val userService: UserService,
        private val vehicleService: VehicleService) {

    fun secureSave(rental: Rental): Result<Rental> = generalService.secureSave(value = rental, function = rentalRepository::save)

    fun save(rental: Rental): Result<Rental> {
        return saveValidate(rental)
                .flatMap { r -> addUserVehicleAndLocationsToTheRental(r, vehicleService::getById, userService::getUserByName, locationService::getById) }
                .flatMap(::secureSave)
    }

    fun saveValidate(rental: Rental): Result<Rental> {
        return rentalSaveDateValidator.valid(rental, ::RuntimeException)
                .flatMap { rentalSaveValidator.valid(it, ::RuntimeException) }
    }

    fun changeRentalVehicle(rental: Rental): Result<Pair<Vehicle, Result<Rental>>> {
        return rentalChangeVehicleValidator.valid(rental, ::RuntimeException)
                .flatMap(this::getById)
                .flatMap { oldRental ->
                    addUserVehicleAndLocationsToTheRental(rental, vehicleService::getById, userService::getUserByName, locationService::getById)
                            .map { newRental -> Pair(oldRental.vehicle, this.secureSave(newRental)) }
                }
    }

    fun addUserVehicleAndLocationsToTheRental(rental: Rental, fetchVehicle: (Int) -> Result<Vehicle>, fetchUser: (String) -> Result<User>, fetchLocation: (Int) -> Result<Location>): Result<Rental> {
        return fetchVehicle.invoke(rental.vehicle.id)
                .flatMap { vehicle ->
                    fetchUser.invoke(rental.user.username)
                            .flatMap { user ->
                                fetchLocation.invoke(rental.startLocation.id)
                                        .flatMap { startLocation ->
                                            fetchLocation.invoke(rental.endLocation.id)
                                                    .map { endLocation -> with(rental) { Rental(id, dateOfRent, dateOfReturn, vehicle, user, startLocation, endLocation) } }
                                        }
                            }
                }
    }

    fun getById(rental: Rental): Result<Rental> = generalService.getByValue(
            value = rental.id,
            error = NotFoundElementException("RENTAL ID DOES NOT EXIST"),
            function = rentalRepository::findById
    )

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