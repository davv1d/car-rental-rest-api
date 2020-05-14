package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.dto.VehicleLocationDto
import com.davv1d.carrental.fuctionHighLevel.writeAndThrowError
import com.davv1d.carrental.mapper.VehicleLocationMapper
import com.davv1d.carrental.service.VehicleLocationService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
class VehicleLocationController(
        private val vehicleLocationService: VehicleLocationService,
        private val vehicleLocationMapper: VehicleLocationMapper
) {
    private val logger = LoggerFactory.getLogger(VehicleLocationController::class.java)

//    2020-02-27T20:40
    @GetMapping(value = ["/vehicle-location"], params = ["date", "vehicleId"])
    fun getVehicleLocationsToSpecificDateByVehicleId(date: String, vehicleId: Int): List<VehicleLocationDto> =
            vehicleLocationMapper.mapToVehicleLocationDtoList(vehicleLocationService.getLocationsToSpecificDateByVehicleId(LocalDateTime.parse(date), vehicleId))

    @PostMapping(value = ["/vehicle-location"])
    fun save(@RequestBody @Valid vehicleLocationDto: VehicleLocationDto) =
            vehicleLocationService.saveWithValidation(vehicleLocationMapper.mapToVehicleLocation(vehicleLocationDto))
                    .forEach(onSuccess = { logger.info(it.toString()) }, onFailure = { writeAndThrowError(logger, it) })

    @DeleteMapping(value = ["/vehicle-location"], params = ["id"])
    fun delete(id: Int) {
        vehicleLocationService.delete(id)
    }
}