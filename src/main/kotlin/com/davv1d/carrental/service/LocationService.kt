package com.davv1d.carrental.service

import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service

@Service
class LocationService(
        private val locationDbValidator: ConditionValidator<Location>,
        private val removeLocationValidator: ConditionValidator<Int>,
        private val locationRepository: LocationRepository,
        private val generalService: GeneralService) {

    fun secureSave(location: Location): Result<Location> = generalService.secureSave(location, locationRepository::save)
    fun save(location: Location): Result<Location> {
        return locationDbValidator.dbValidate(location)
                .flatMap { this.secureSave(it) }
    }

    fun getLocationOrThrow(location: Location): Location = locationRepository.findLocation(location.city, location.street) ?: throw IllegalStateException("Illegal operation")

    fun getAllLocations(): List<Location> = locationRepository.findAll()
    fun getAllLocationsByCity(city: String): List<Location> = locationRepository.findAllByCity(city)
    fun getLocation(location: Location): Result<Location> {
        with(location) {
            return when (val result = locationRepository.findLocation(city, street)) {
                null -> Result.failure(NotFoundElementException("This location is not exist"))
                else -> Result.invoke(result)
            }
        }
    }

    fun deleteLocationById(id: Int): Result<Unit> {
        return removeLocationValidator.dbValidate(id)
                .map { locationRepository.deleteById(it) }
    }
}