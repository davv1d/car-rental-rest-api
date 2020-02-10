package com.davv1d.carrental.service

import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.VehicleRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Component

@Component
class VehicleService(
        private val vehicleRepository: VehicleRepository,
        private val vehicleValidator: ConditionValidator<Vehicle>,
        private val generalService: GeneralService,
        private val locationService: LocationService) {

    fun getAllVehicle(): List<Vehicle> = vehicleRepository.findAll()

    fun save(vehicle: Vehicle): Result<Vehicle> {
        return vehicleValidator.dbValidate(vehicle)
                .map { locationService.getLocationOrThrow(vehicle.location) }
                .map { location -> with(vehicle) { Vehicle(registration = registration, brand = brand, model = model, dailyFee = dailyFee, location = location, bodyType = bodyType, productionYear = productionYear, fuelType = fuelType, power = power) } }
                .flatMap { secureSave(it) }
    }

    private fun secureSave(vehicle: Vehicle): Result<Vehicle> = generalService.secureSave(vehicle, vehicleRepository::save)
}