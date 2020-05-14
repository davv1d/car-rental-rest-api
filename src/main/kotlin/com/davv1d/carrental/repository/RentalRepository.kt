package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Rental
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.transaction.Transactional

@Repository
@Transactional
interface RentalRepository : CrudRepository<Rental, Int> {
    override fun findAll(): List<Rental>

    fun findByVehicle_Id(vehicleId: Int): List<Rental>

    fun findByDateOfOrderIsAfter(date: LocalDateTime): List<Rental>

    fun findByUser_Username(username: String): List<Rental>

    fun findByStartLocation_Id(locationId: Int): List<Rental>

    @Query(value = "select case when count(r) = 0 then true else false end from Rental r where r.vehicle.id = :vehicleId")
    fun isVehicleNotUsedInRental(vehicleId: Int): Boolean

    @Query(value = "select case when count(r) = 0 then true else false end from Rental r where r.startLocation.id = :locationId or r.endLocation.id = :locationId")
    fun isLocationNotUsedInRental(locationId: Int): Boolean

    @Query(value = "select case when count(r) > 0 then true else false end from Rental r where r.id = :rentalId and r.dateOfRent = :dateOfRent and r.dateOfReturn = :dateOfReturn " +
            "and r.startLocation.id = :startLocationId and r.endLocation.id = :endLocationId and r.user.username like :username")
    fun doesRentalExist(rentalId: Int, dateOfRent: LocalDateTime, dateOfReturn: LocalDateTime, startLocationId: Int, endLocationId: Int, username: String): Boolean
}