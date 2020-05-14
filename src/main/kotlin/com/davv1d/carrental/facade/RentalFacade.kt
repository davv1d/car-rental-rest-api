package com.davv1d.carrental.facade

import com.davv1d.carrental.domain.Rental
import com.davv1d.carrental.domain.VehicleLocation
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.service.RentalService
import com.davv1d.carrental.service.VehicleLocationService
import org.springframework.stereotype.Component

@Component
class RentalFacade(
        private val rentalService: RentalService,
        private val vehicleLocationService: VehicleLocationService
) {

    fun saveRental(rental: Rental): Result<Rental> {
        return rentalService.save(rental)
                .flatMap { savedRental ->
                    val vehicleLocation = VehicleLocation(date = savedRental.dateOfReturn, location = savedRental.endLocation, vehicle = savedRental.vehicle)
                    vehicleLocationService.vehicleLocationSecureSave(vehicleLocation)
                            .map { savedRental }
                }
    }

    fun deleteRental(rentalId: Int): Result<Pair<Rental, VehicleLocation>> {
        return rentalService.delete(rentalId)
                .flatMap { rental ->
                    vehicleLocationService.getByDateAndVehicleId(rental.dateOfReturn, rental.vehicle.id)
                            .map { vehicleLocation ->
                                vehicleLocationService.delete(vehicleLocation.id)
                                return@map Pair(rental, vehicleLocation)
                            }
                }
    }

    fun changeRentalVehicle(rental: Rental): Result<Rental> {
        return rentalService.changeRentalVehicle(rental)
                .flatMap { oldVehicleAndRental ->
                    oldVehicleAndRental.second
                            .map { newRental ->
                                val vehicleLocation = VehicleLocation(date = newRental.dateOfReturn, location = newRental.endLocation, vehicle = newRental.vehicle)
                                vehicleLocationService.vehicleLocationSecureSave(vehicleLocation).map { newRental }
                                vehicleLocationService.getByDateAndVehicleId(newRental.dateOfReturn, oldVehicleAndRental.first.id)
                                        .map { vl -> vehicleLocationService.delete(vl.id) }
                                return@map newRental
                            }
                }
    }
}