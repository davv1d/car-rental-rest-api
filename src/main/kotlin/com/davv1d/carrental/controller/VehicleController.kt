package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.VehicleDto
import com.davv1d.carrental.fuctionHighLevel.writeAndThrowError
import com.davv1d.carrental.mapper.VehicleMapper
import com.davv1d.carrental.service.VehicleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class VehicleController(private val vehicleService: VehicleService, private val vehicleMapper: VehicleMapper) {
    private val logger: Logger = LoggerFactory.getLogger(VehicleController::class.java)

    @PostMapping(value = ["/vehicle"])
    fun saveVehicle(@RequestBody @Valid vehicleDto: VehicleDto) {
        val vehicle = vehicleMapper.mapToVehicle(vehicleDto)
        vehicleService.save(vehicle)
                .forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
    }

    @GetMapping(value = ["/vehicle"])
    fun getAllVehicles(): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getAll())

    @GetMapping(value = ["/vehicle"], params = ["registration"])
    fun getByRegistration(registration: String): VehicleDto {
        return vehicleService.getByRegistration(registration)
                .effect(onSuccess = vehicleMapper::mapToVehicleDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { VehicleDto() })
    }

    @GetMapping(value = ["/vehicle"], params = ["brand"])
    fun getByBrand(brand: String): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getByBrand(brand))


    @GetMapping(value = ["/vehicle"], params = ["fuelType"])
    fun getByFuelType(fuelType: String): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getByFuelType(fuelType))


    @GetMapping(value = ["/vehicle"], params = ["city", "street"])
    fun getByLocation(city: String, street: String): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getByLocation(city, street))
}