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

    @Query(value = "select case when count(v) > 0 then true else false end from Vehicle v where upper(v.registration) like upper(:registration)")
    fun doesRegistrationExist(registration: String): Boolean

    @Query(value = "select v from Vehicle v where upper(v.registration) like upper(:registration)")
    fun findByRegistration(registration: String): Vehicle?

    @Query(value = "select v from Vehicle v where upper(v.brand) like upper(:brand)")
    fun findByBrand(brand: String): List<Vehicle>

    @Query(value = "select v from Vehicle v where upper(v.fuelType) like upper(:fuelType)")
    fun findByFuelType(fuelType: String): List<Vehicle>

    @Query(nativeQuery = true, value = "select * from VEHICLES V inner join LOCATIONS L on V.LOCATION_ID = L.ID where upper(L.CITY) like upper(:city) and upper(L.STREET) like upper(:street)")
    fun findByLocation(city: String, street: String): List<Vehicle>
}