package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Vehicle
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface VehicleRepository : CrudRepository<Vehicle, Int> {
    override fun findAll(): List<Vehicle>

    @Query(value = "select case when count(v) > 0 then true else false end from Vehicle v where v.registration like :registration")
    fun isRegistrationExist(registration: String): Boolean
}