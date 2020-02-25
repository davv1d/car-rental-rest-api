package com.davv1d.carrental.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "VEHICLE_LOCATION")
data class VehicleLocation(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "DATE", nullable = false)
        val date: LocalDateTime,
        @ManyToOne(targetEntity = Location::class)
        @JoinColumn(name = "LOCATION_ID")
        val location: Location,
        @ManyToOne(targetEntity = Vehicle::class)
        @JoinColumn(name = "VEHICLE_ID")
        val vehicle: Vehicle
)