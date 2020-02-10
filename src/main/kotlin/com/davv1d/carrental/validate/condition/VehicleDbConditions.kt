package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component

@Component
class VehicleDbConditions(private val vehicleRepository: VehicleRepository, private val locationRepository: LocationRepository) : ConditionGenerator<Vehicle> {
    override fun get(value: Vehicle): List<Condition<Vehicle>> {
        val condition1 = Condition(value, "REGISTRATION NUMBER IS EXIST", { vehicle -> vehicleRepository.isRegistrationExist(vehicle.registration) })
        val condition2 = Condition(value, "LOCATION DOES NOT EXIST", { vehicle -> with(vehicle.location) { locationRepository.isLocationNotExist(city, street) } })
        return listOf(condition1, condition2)
    }
}