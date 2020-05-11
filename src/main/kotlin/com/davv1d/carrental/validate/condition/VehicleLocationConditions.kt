package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.VehicleLocation
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component

@Component
class VehicleLocationConditions(
        private val vehicleRepository: VehicleRepository,
        private val locationRepository: LocationRepository) {

    fun fetchVehicleLocationSaveConditions(): List<Condition<VehicleLocation>> {
        val condition1 = Condition<VehicleLocation>("LOCATION NOT EXIST") { vehicleLocation -> locationRepository.doesLocationNotExistById(vehicleLocation.location.id) }
        val condition2 = Condition<VehicleLocation>("VEHICLE NOT EXIST") { vehicleLocation -> vehicleRepository.doesIdNotExist(vehicleLocation.vehicle.id) }
        return listOf(condition1, condition2)
    }
}