package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.VehicleLocation
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
}