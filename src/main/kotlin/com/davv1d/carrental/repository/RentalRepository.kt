package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Rental
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
}