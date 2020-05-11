package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.repository.LocationRepository
import org.springframework.stereotype.Component

@Component
class LocationDbConditions(val locationRepository: LocationRepository) {
    val LOCATION_EXIST_IN_DB = "Location exist in database"

    fun fetchLocationSaveConditions(): List<Condition<Location>> {
        val con1 = Condition<Location>(LOCATION_EXIST_IN_DB)
        { location -> with(location) { locationRepository.doesLocationExist(city, street) } }
        return listOf(con1)
    }

    fun fetchLocationRemoveConditions(): List<Condition<Int>> {
        val condition1 = Condition<Int>( "LOCATION WITH THIS ID NOT EXIST") { locationRepository.doesLocationNotExistById(it) }
        val condition2 = Condition<Int>( "IN THIS LOCATION THERE ARE CARS") { locationRepository.areThereVehiclesInLocation(it) }
        return listOf(condition1, condition2)
    }
}