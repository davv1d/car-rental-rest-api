package com.davv1d.carrental.service

import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.VehicleRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Component

@Component
class VehicleService(
        private val vehicleRepository: VehicleRepository,
        private val vehicleValidator: ConditionValidator<Vehicle>,
        private val generalService: GeneralService,
        private val locationService: LocationService,
        private val updateVehicleValidator: ConditionValidator<Vehicle>) {

    fun getAll(): List<Vehicle> = vehicleRepository.findAll()

    fun getByRegistration(registration: String): Result<Vehicle> = generalService.getByValue(
            value = registration,
            error = NotFoundElementException("VEHICLE_WITH_THIS_REGISTRATION_IS_NOT_EXIST"),
            function = vehicleRepository::findByRegistration)

    fun getByBrand(brand: String): List<Vehicle> = vehicleRepository.findByBrand(brand)

    fun getByFuelType(fuelType: String): List<Vehicle> = vehicleRepository.findByFuelType(fuelType)

    fun getByLocation(city: String, street: String) = vehicleRepository.findByLocation(city, street)

    fun save(vehicle: Vehicle): Result<Vehicle> = saveWithValidation(vehicle, vehicleValidator)

    fun updateVehicle(vehicle: Vehicle): Result<Vehicle> = saveWithValidation(vehicle, updateVehicleValidator)

    private fun saveWithValidation(vehicle: Vehicle, validator: ConditionValidator<Vehicle>): Result<Vehicle> {
       return validator.dbValidate(vehicle)
                .map { locationService.getLocationOrThrow(vehicle.location) }
                .map { location -> with(vehicle) { Vehicle(id = id, registration = registration, brand = brand, model = model, dailyFee = dailyFee, location = location, bodyType = bodyType, productionYear = productionYear, fuelType = fuelType, power = power) } }
                .flatMap { secureSave(it) }
    }

    private fun secureSave(vehicle: Vehicle): Result<Vehicle> = generalService.secureSave(vehicle, vehicleRepository::save)
}