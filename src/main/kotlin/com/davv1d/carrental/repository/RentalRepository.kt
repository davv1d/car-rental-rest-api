package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Rental
import com.davv1d.carrental.domain.Vehicle
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import javax.transaction.Transactional

@Repository
@Transactional
interface RentalRepository : CrudRepository<Rental, Int> {
    override fun findAll(): List<Rental>
}