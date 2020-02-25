package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.VehicleLocation
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.VehicleRepository
import org.springframework.stereotype.Component

@Component
class VehicleLocationConditions(private val vehicleRepository: VehicleRepository, private val locationRepository: LocationRepository) : ConditionGenerator<VehicleLocation> {
    override fun get(value: VehicleLocation): List<Condition<VehicleLocation>> {
        val condition1 = Condition(value, "LOCATION NOT EXIST", { locationRepository.doesLocationNotExistById(value.location.id) })
        val condition2 = Condition(value, "VEHICLE NOT EXIST", { vehicleRepository.doesIdNotExist(value.vehicle.id) })
        return listOf(condition1, condition2)
    }
}