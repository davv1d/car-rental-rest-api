package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.dto.VehicleDto
import com.davv1d.carrental.fuctionHighLevel.writeAndThrowError
import com.davv1d.carrental.mapper.VehicleMapper
import com.davv1d.carrental.service.VehicleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.validation.Valid

@RestController
class VehicleController(private val vehicleService: VehicleService, private val vehicleMapper: VehicleMapper) {
    private val logger: Logger = LoggerFactory.getLogger(VehicleController::class.java)

    @PostMapping(value = ["/vehicle"])
    fun saveVehicle(@RequestBody @Valid vehicleDto: VehicleDto) =
            vehicleService.save(vehicleMapper.mapToVehicle(vehicleDto))
                    .forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })

    @GetMapping(value = ["/vehicle"])
    fun getAllVehicles(): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getAll())

    @GetMapping(value = ["/vehicle"], params = ["registration"])
    fun getByRegistration(registration: String): VehicleDto =
            vehicleService.getByRegistration(registration)
                    .effect(onSuccess = vehicleMapper::mapToVehicleDto, onFailure = { exception -> logger.error(exception.message) }, onEmpty = { VehicleDto() })

    @GetMapping(value = ["/vehicle"], params = ["brand"])
    fun getByBrand(brand: String): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getByBrand(brand))

    @GetMapping(value = ["/vehicle"], params = ["fuelType"])
    fun getByFuelType(fuelType: String): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getByFuelType(fuelType))

    @GetMapping(value = ["/vehicle"], params = ["date", "locationId"])
    fun getByLocation(date: String, locationId: Int): List<VehicleDto> = vehicleMapper.mapToVehicleDtoList(vehicleService.getByLocation(LocalDateTime.parse(date), locationId))

    @GetMapping(value = ["/vehicle"], params = ["dateOfRent", "dateOfReturn", "locationId"])
    fun getAvailable(dateOfRent: String, dateOfReturn: String, locationId: Int): List<VehicleDto> =
            vehicleMapper.mapToVehicleDtoList(vehicleService.getAvailable(LocalDateTime.parse(dateOfRent), LocalDateTime.parse(dateOfReturn), locationId))

    @PutMapping(value = ["/vehicle"])
    fun update(@RequestBody @Valid vehicleDto: VehicleDto): VehicleDto =
         vehicleService.update(vehicleMapper.mapToVehicle(vehicleDto))
                .effect(onSuccess = vehicleMapper::mapToVehicleDto, onFailure = { writeAndThrowError(logger, it) }, onEmpty = { VehicleDto() })

    @DeleteMapping(value = ["/vehicle"], params = ["vehicleId"])
    fun deleteById(vehicleId: Int) {
        vehicleService.deleteById(vehicleId)
                .forEach(onSuccess = { println("delete vehicle with id = $vehicleId")}, onFailure = { throw it })
    }
}