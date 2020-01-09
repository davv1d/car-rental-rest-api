package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.LocationDto
import com.davv1d.carrental.mapper.LocationMapper
import com.davv1d.carrental.service.LocationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LocationController(private val locationService: LocationService, private val locationMapper: LocationMapper) {

    @PostMapping(value = ["/location"])
    fun saveLocation(@RequestBody locationDto: LocationDto) {
        locationService.save(location = locationMapper.mapToLocation(locationDto))
                .forEach(onSuccess = ::println, onFailure = { throw it })
    }

    @GetMapping(value = ["/location"])
    fun getAllLocations(): List<LocationDto> {
        return locationMapper.mapToLocationDtoList(locationService.getAllLocations())
    }

}