package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.repository.LocationRepository
import org.springframework.stereotype.Component

@Component
class LocationDbConditions(val locationRepository: LocationRepository) : ConditionGenerator<Location> {
    val LOCATION_EXIST_IN_DB = "Location exist in database"
    override fun get(): List<Condition<Location>> {
        val con1 = Condition<Location>(LOCATION_EXIST_IN_DB)
        { location -> with(location) { locationRepository.doesLocationExist(city, street) } }
        return listOf(con1)
    }
}