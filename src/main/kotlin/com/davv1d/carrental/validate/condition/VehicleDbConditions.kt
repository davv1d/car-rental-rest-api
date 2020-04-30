package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component

@Component
class VehicleDbConditions(private val vehicleRepository: VehicleRepository) : ConditionGenerator<Vehicle> {
    override fun get(): List<Condition<Vehicle>> {
        val condition1 = Condition<Vehicle>("REGISTRATION NUMBER IS EXIST") { vehicle -> vehicleRepository.doesRegistrationExist(vehicle.registration) }
        return listOf(condition1)
    }
}