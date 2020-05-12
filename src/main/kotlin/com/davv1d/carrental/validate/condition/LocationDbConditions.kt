package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.RentalRepository
import com.davv1d.carrental.repository.VehicleLocationRepository
import org.springframework.stereotype.Component

@Component
class LocationDbConditions(val locationRepository: LocationRepository,
                           val vehicleLocationRepository: VehicleLocationRepository,
                           val rentalRepository: RentalRepository) {
    val LOCATION_EXIST_IN_DB = "Location exist in database"

    fun fetchLocationSaveConditions(): List<Condition<Location>> {
        val con1 = Condition<Location>(LOCATION_EXIST_IN_DB) { l -> locationRepository.doesLocationNotExist(l.city, l.street) }
        return listOf(con1)
    }

    fun fetchLocationRemoveConditions(): List<Condition<Int>> {
        val condition1 = Condition<Int>("LOCATION WITH THIS ID NOT EXIST") { locationRepository.existsById(it) }
        val condition2 = Condition<Int>("IN THIS LOCATION THERE ARE CARS") { vehicleLocationRepository.isLocationNotUsedInVehicleLocation(it) }
        val condition3 = Condition<Int>("Location is used in rental") { rentalRepository.isLocationNotUsedInRental(it) }
        return listOf(condition1, condition2, condition3)
    }
}