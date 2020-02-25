package com.davv1d.carrental.service

import com.davv1d.carrental.domain.VehicleLocation
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.VehicleLocationRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class VehicleLocationService(
        private val generalService: GeneralService,
        private val vehicleService: VehicleService,
        private val locationService: LocationService,
        private val vehicleLocationRepository: VehicleLocationRepository,
        private val vehicleLocationValidator: ConditionValidator<VehicleLocation>
) {

    fun saveWithValidation(vehicleLocation: VehicleLocation): Result<VehicleLocation> {
        return vehicleLocationValidator.dbValidate(vehicleLocation)
                .flatMap { vL ->
                    vehicleService.getById(vL.vehicle.id)
                            .flatMap { v ->
                                locationService.getById(vL.location.id)
                                        .map { l -> VehicleLocation(date = vL.date, location = l, vehicle = v) }
                            }
                }.flatMap { vehicleLocationSecureSave(it) }
    }

    fun getLocationsToSpecificDateByVehicleId(date1: LocalDateTime, vehicleId: Int): List<VehicleLocation> = vehicleLocationRepository.findVehicleLocationsToSpecificDateByVehicleId(date1, vehicleId)

    fun vehicleLocationSecureSave(vehicleLocation: VehicleLocation): Result<VehicleLocation> = generalService.secureSave(vehicleLocation, vehicleLocationRepository::save)
}