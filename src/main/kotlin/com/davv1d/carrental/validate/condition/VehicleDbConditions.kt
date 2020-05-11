package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component

@Component
class VehicleDbConditions(private val vehicleRepository: VehicleRepository) {

    fun fetchVehicleSaveConditions(): List<Condition<Vehicle>> {
        val condition1 = Condition<Vehicle>("REGISTRATION NUMBER IS EXIST") { vehicle -> vehicleRepository.doesRegistrationExist(vehicle.registration) }
        return listOf(condition1)
    }

    fun fetchVehicleUpdateConditions(): List<Condition<Vehicle>> {
        val condition1 = Condition<Vehicle>("ID DOES NOT EXIST") { vehicle -> vehicleRepository.doesIdNotExist(vehicle.id) }
        val condition2 = Condition<Vehicle>("REGISTRATION NUMBER DOES EXIST") { v -> vehicleRepository.doesRegistrationExistOrNotBelongToTheVehicleWithThisId(v.registration, v.id) }
        return listOf(condition1, condition2)
    }

}