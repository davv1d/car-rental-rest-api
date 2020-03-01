package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Vehicle
import com.davv1d.carrental.domain.VehicleLocation
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
interface VehicleRepository : CrudRepository<Vehicle, Int> {
    override fun findAll(): List<Vehicle>

    @Query(value = "select case when count(v) > 0 then true else false end from Vehicle v where upper(v.registration) like upper(:registration)")
    fun doesRegistrationExist(registration: String): Boolean

    @Query(value = "select case when count(v) = 0 then true else false end from Vehicle v where v.id = :id")
    fun doesIdNotExist(id: Int): Boolean

    @Query(value = "select v from Vehicle v where upper(v.registration) like upper(:registration)")
    fun findByRegistration(registration: String): Optional<Vehicle>

    @Query(value = "select v from Vehicle v where upper(v.brand) like upper(:brand)")
    fun findByBrand(brand: String): List<Vehicle>

    @Query(value = "select v from Vehicle v where upper(v.fuelType) like upper(:fuelType)")
    fun findByFuelType(fuelType: String): List<Vehicle>

    @Query(nativeQuery = true,
            value = "select case when count(*) > 0 then true else false end from VEHICLES as v where (v.id = :id and v.registration not like :registration) and " +
                    "(select count(*) from vehicles as v where upper(v.registration) like upper(:registration))")
    fun doesRegistrationExistUpdateVehicle(registration: String, id: Int): Boolean

    @Query(nativeQuery = true, value = "select * from vehicles as v join (select a.id, a.date, a.location_id, a.vehicle_id from vehicle_location as a join " +
            "(select vehicle_id, max(date) as max from vehicle_location where date <= :date1 group by vehicle_id) b on a.vehicle_id = b.vehicle_id and a.date = b.max and a.location_id = :locationId) c on v.id = c.vehicle_id")
    fun findVehiclesByLocation(date1: LocalDateTime, locationId: Int): List<Vehicle>

    @Query(nativeQuery = true,
            value = "select v.ID, v.REGISTRATION, v.BRAND, v.MODEL, v.DAILY_FEE, v.BODY_TYPE, v.PRODUCTION_YEAR, v.FUEL_TYPE, v.POWER from vehicles as v " +
                    "left join rentals on v.id = rentals.vehicle_id where (rentals.date_of_rent is null or (:dateOfRent < rentals.date_of_rent and :dateOfReturn < rentals.date_of_rent) or (:dateOfRent > rentals.date_of_return and :dateOfReturn > rentals.date_of_return)) " +
                    "and v.id in " +
                    "(select v.id from vehicles as v join (select a.id, a.date, a.location_id, a.vehicle_id from vehicle_location as a join " +
                    "(select vehicle_id, max(date) as max from vehicle_location where date <= :dateOfRent group by vehicle_id) b " +
                    "on a.vehicle_id = b.vehicle_id and a.date = b.max and a.location_id = :locationId) c on v.id = c.vehicle_id)")
    fun findAvailableVehicles(dateOfRent: LocalDateTime, dateOfReturn: LocalDateTime, locationId: Int): List<Vehicle>

    @Query(nativeQuery = true,
            value = "select case when count(*) = 0 then 'true' else 'false' end " +
                    "from vehicles as v " +
                    "left join rentals on v.id = rentals.vehicle_id where (rentals.date_of_rent is null or (:dateOfRent < rentals.date_of_rent and :dateOfReturn < rentals.date_of_rent) or (:dateOfRent > rentals.date_of_return and :dateOfReturn > rentals.date_of_return)) " +
                    "and v.id in " +
                    "(select v.id from vehicles as v join (select a.id, a.date, a.location_id, a.vehicle_id from vehicle_location as a join " +
                    "(select vehicle_id, max(date) as max from vehicle_location where date <= :dateOfRent group by vehicle_id) b " +
                    "on a.vehicle_id = b.vehicle_id and a.date = b.max and a.location_id = :locationId) c on v.id = c.vehicle_id where v.id = :vehicleId)")
    fun doesVehicleNotExistInAvailableVehicles(dateOfRent: LocalDateTime, dateOfReturn: LocalDateTime, locationId: Int, vehicleId: Int): Boolean
}