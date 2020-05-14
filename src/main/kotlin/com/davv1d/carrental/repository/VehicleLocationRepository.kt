package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.VehicleLocation
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
interface VehicleLocationRepository : CrudRepository<VehicleLocation, Int> {
    override fun findAll(): List<VehicleLocation>

    @Query(value = "select vl from VehicleLocation vl where vl.date <= :date and  vl.vehicle.id = :vehicleId")
    fun findVehicleLocationsToSpecificDateByVehicleId(date: LocalDateTime, vehicleId: Int): List<VehicleLocation>

    fun findByDateAndVehicle_Id(date: LocalDateTime, vehicleId: Int): Optional<VehicleLocation>

    @Query(value = "select case when count(vl) = 0 then true else false end from VehicleLocation as vl where vl.location.id = :locationId")
    fun isLocationNotUsedInVehicleLocation(locationId: Int): Boolean

    @Modifying
    @Query(value = "delete from VehicleLocation as vl where vl.vehicle.id = :vehicleId")
    fun deleteAllByVehicleId(vehicleId: Int)
}