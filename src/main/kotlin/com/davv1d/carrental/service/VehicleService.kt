package com.davv1d.carrental.service

import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.VehicleLocationRepository
import com.davv1d.carrental.repository.VehicleRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VehicleService(
        private val vehicleRepository: VehicleRepository,
        private val generalService: GeneralService,
        private val vehicleValidator: ConditionValidator<Vehicle>,
        private val updateVehicleValidator: ConditionValidator<Vehicle>,
        private val vehicleRemovalValidator: ConditionValidator<Int>,
        private val vehicleLocationRepository: VehicleLocationRepository) {

    fun getAll(): List<Vehicle> = vehicleRepository.findAll()

    fun getById(id: Int): Result<Vehicle> = generalService.getByValue(
            value = id,
            error = NotFoundElementException("VEHICLE_WITH_THIS_ID_IS_NOT_EXIST"),
            function = vehicleRepository::findById)

    fun getByRegistration(registration: String): Result<Vehicle> = generalService.getByValue(
            value = registration,
            error = NotFoundElementException("VEHICLE_WITH_THIS_REGISTRATION_IS_NOT_EXIST"),
            function = vehicleRepository::findByRegistration)

    fun getByBrand(brand: String): List<Vehicle> = vehicleRepository.findByBrand(brand)

    fun getByFuelType(fuelType: String): List<Vehicle> = vehicleRepository.findByFuelType(fuelType)

    fun getByLocation(data1: LocalDateTime, locationId: Int) = vehicleRepository.findVehiclesByLocation(data1, locationId)

    fun save(vehicle: Vehicle): Result<Vehicle> = saveWithValidation(vehicle, vehicleValidator)

    fun update(vehicle: Vehicle): Result<Vehicle> = saveWithValidation(vehicle, updateVehicleValidator)

    fun getAvailable(dateOfRent: LocalDateTime, dateOfReturn: LocalDateTime, locationId: Int): List<Vehicle> = vehicleRepository.findAvailableVehicles(dateOfRent, dateOfReturn, locationId)

    private fun saveWithValidation(vehicle: Vehicle, validator: ConditionValidator<Vehicle>): Result<Vehicle> {
        return validator.valid(vehicle, ::RuntimeException)
                .flatMap { secureSave(it) }
    }

    fun deleteById(vehicleId: Int): Result<Unit> {
        return vehicleRemovalValidator.valid(vehicleId, ::RuntimeException)
                .map { vehicleRepository.deleteById(vehicleId) }
                .map { vehicleLocationRepository.deleteAllByVehicleId(vehicleId) }
    }

    private fun secureSave(vehicle: Vehicle): Result<Vehicle> = generalService.secureSave(vehicle, vehicleRepository::save)
}