package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.repository.LocationRepository
import org.springframework.stereotype.Component

@Component
class RemoveLocationConditions(private val locationRepository: LocationRepository) : ConditionGenerator<Int> {
    override fun get(): List<Condition<Int>> {
        val condition1 = Condition<Int>( "LOCATION WITH THIS ID NOT EXIST") { locationRepository.doesLocationNotExistById(it) }
        val condition2 = Condition<Int>( "IN THIS LOCATION THERE ARE CARS") { locationRepository.areThereVehiclesInLocation(it) }
        return listOf(condition1, condition2)
    }
}