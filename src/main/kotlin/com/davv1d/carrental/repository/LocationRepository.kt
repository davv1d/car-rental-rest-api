package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Location
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface LocationRepository : CrudRepository<Location, Int> {
    @Query(value = "select case when count(l) > 0 then true else false end from Location l where " +
            "upper(l.city) like upper(:city) and upper(l.street) like upper(:street)")
    fun isLocationExist(city: String, street: String): Boolean

    override fun findAll(): List<Location>

    @Query(value = "select case when count(l) = 0 then true else false end from Location l where " +
            "upper(l.city) like upper(:city) and upper(l.street) like upper(:street)")
    fun isLocationNotExist(city: String, street: String): Boolean

    @Query(value = "select l from Location l where upper(l.city) like upper(:city)")
    fun findAllByCity(city: String): List<Location>

    @Query(value = "select l from Location l where " +
            "upper(l.city) like upper(:city) and upper(l.street) like upper(:street)")
    fun findLocation(city: String, street: String): Location?

    @Query(value = "select case when count(l) = 0 then true else false end from Location l where l.id like :id")
    fun doesNotExistById(id: Int): Boolean
}