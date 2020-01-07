package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
@Table(name = "VEHICLES_PARAMETERS")
data class VehicleParameters(
        @Id
        val id: Int = 0,
        @Column(name = "BODY_TYPE", length = 20, nullable = false)
        val bodyType: String,
        @Column(name = "PRODUCTION_YEAR", length = 4, nullable = false)
        val productionYear: Int,
        @Column(name = "FUEL_TYPE", length = 10, nullable = false)
        val fuelType: String,
        @Column(name = "POWER", length = 4, nullable = false)
        val power: Int,
        @OneToOne
        @MapsId
        val vehicle: Vehicle
)