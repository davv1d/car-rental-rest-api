package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Vehicle
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface VehicleRepository : CrudRepository<Vehicle, Int> {
}