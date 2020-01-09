package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.domain.LocationDto
import org.springframework.stereotype.Component

@Component
class LocationMapper {
    fun mapToLocation(locationDto: LocationDto): Location = with(locationDto) { Location(city = city, street = street) }
    fun mapToLocationDto(location: Location): LocationDto = with(location) { LocationDto(city, street) }
    fun mapToLocationDtoList(locations: List<Location>): List<LocationDto> {
        return locations.asSequence()
                .map(this::mapToLocationDto)
                .toList()
    }
}