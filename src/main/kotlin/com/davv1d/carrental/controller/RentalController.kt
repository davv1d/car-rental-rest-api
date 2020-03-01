package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.dto.RentalDto
import com.davv1d.carrental.domain.dto.SaveRentalDto
import com.davv1d.carrental.facade.RentalFacade
import com.davv1d.carrental.fuctionHighLevel.writeAndThrowError
import com.davv1d.carrental.mapper.RentalMapper
import com.davv1d.carrental.service.RentalService
import com.davv1d.carrental.service.VehicleLocationService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
class RentalController(
        private val rentalService: RentalService,
        private val rentalMapper: RentalMapper,
        private val rentalFacade: RentalFacade
) {

    private val logger = LoggerFactory.getLogger(RentalController::class.java)

    @GetMapping(value = ["/rental"])
    fun getAll(): List<RentalDto> = rentalMapper.mapToRentalDtoList(rentalService.getAll())

    @GetMapping(value = ["/rental"], params = ["vehicleId"])
    fun getByVehicleId(vehicleId: Int): List<RentalDto> = rentalMapper.mapToRentalDtoList(rentalService.getByVehicleId(vehicleId))

    @DeleteMapping(value = ["/rental"], params = ["rentalId"])
    fun delete(rentalId: Int) {
        rentalFacade.deleteRental(rentalId)
                .forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
    }

    @PostMapping(value = ["/rental"])
    fun save(@RequestBody @Valid saveRentalDto: SaveRentalDto) {
        rentalFacade.saveRental(rentalMapper.mapToRental(saveRentalDto))
                .forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
    }
}