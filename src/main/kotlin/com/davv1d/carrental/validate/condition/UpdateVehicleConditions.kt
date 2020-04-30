package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component

@Component
class UpdateVehicleConditions(private val vehicleRepository: VehicleRepository) : ConditionGenerator<Vehicle> {
    override fun get(): List<Condition<Vehicle>> {
        val condition1 = Condition<Vehicle>( "ID DOES NOT EXIST") { vehicle -> !vehicleRepository.doesIdNotExist(vehicle.id) }
        val condition2 = Condition<Vehicle>( "REGISTRATION NUMBER DOES EXIST") { v -> vehicleRepository.doesRegistrationExistUpdateVehicle(v.registration, v.id) }
        return listOf(condition1, condition2)
    }
}