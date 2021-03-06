package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component

@Component
class UpdateVehicleConditions(private val vehicleRepository: VehicleRepository, private val locationRepository: LocationRepository) : ConditionGenerator<Vehicle> {
    override fun get(value: Vehicle): List<Condition<Vehicle>> {
        val condition1 = Condition(value, "ID DOES NOT EXIST", { vehicle -> !vehicleRepository.doesIdNotExist(vehicle.id) })
        val condition2 = Condition(value, "REGISTRATION NUMBER DOES EXIST", { v -> vehicleRepository.doesRegistrationExistUpdateVehicle(v.registration, v.id) })
        val condition3 = Condition(value, "LOCATION DOES NOT EXIST", { v -> locationRepository.doesLocationNotExist(v.location.city, v.location.street) })
        return listOf(condition1, condition2, condition3)
    }
}