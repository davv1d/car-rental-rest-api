package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.dto.LocationDto
import com.davv1d.carrental.mapper.LocationMapper
import com.davv1d.carrental.service.LocationService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class LocationController(private val locationService: LocationService, private val locationMapper: LocationMapper) {

    @PostMapping(value = ["/location"])
    fun saveLocation(@RequestBody @Valid locationDto: LocationDto) {
        locationService.save(location = locationMapper.mapToLocation(locationDto))
                .forEach(onSuccess = ::println, onFailure = { throw it })
    }

    @GetMapping(value = ["/location"])
    fun getAllLocations(): List<LocationDto> {
        return locationMapper.mapToLocationDtoList(locationService.getAllLocations())
    }

    @DeleteMapping(value = ["/location"], params = ["id"])
    fun deleteById(@RequestParam id: Int) {
        locationService.deleteLocationById(id)
                .forEach(onSuccess = { println("delete location with id = $id")}, onFailure = { throw it })
    }
}