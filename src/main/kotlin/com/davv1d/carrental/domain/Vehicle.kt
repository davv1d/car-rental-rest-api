package com.davv1d.carrental.domain

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "VEHICLES")
data class Vehicle(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "REGISTRATION", length = 10, nullable = false)
        val registration: String = "",
        @Column(name = "BRAND", length = 20, nullable = false)
        val brand: String = "",
        @Column(name = "MODEL", length = 20, nullable = false)
        val model: String = "",
        @Column(name = "DAILY_FEE", length = 20, nullable = false)
        val dailyFee: BigDecimal = BigDecimal.ZERO,
        @Column(name = "BODY_TYPE", length = 20, nullable = false)
        val bodyType: String = "",
        @Column(name = "PRODUCTION_YEAR", length = 4, nullable = false)
        val productionYear: Int = 0,
        @Column(name = "FUEL_TYPE", length = 10, nullable = false)
        val fuelType: String = "",
        @Column(name = "POWER", length = 4, nullable = false)
        val power: Int = 0
)