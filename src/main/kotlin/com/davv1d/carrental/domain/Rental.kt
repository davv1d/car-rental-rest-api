package com.davv1d.carrental.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "RENTALS")
data class Rental(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "DATE_OF_RENT", nullable = false)
        val dateOfRent: LocalDateTime,
        @Column(name = "DATE_OF_RETURN", nullable = false)
        val dateOfReturn: LocalDateTime,
        @ManyToOne(targetEntity = Vehicle::class)
        @JoinColumn(name = "VEHICLE_ID")
        val vehicle: Vehicle,
        @ManyToOne(targetEntity = User::class)
        @JoinColumn(name = "USER_ID")
        val user: User,
        @ManyToOne(targetEntity = Location::class)
        @JoinColumn(name = "START_LOCATION_ID")
        val startLocation: Location,
        @ManyToOne(targetEntity = Location::class)
        @JoinColumn(name = "END_LOCATION_ID")
        val endLocation: Location
)