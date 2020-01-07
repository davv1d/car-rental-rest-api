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
        val registration: String,
        @Column(name = "BRAND", length = 20, nullable = false)
        val brand: String,
        @Column(name = "MODEL", length = 20, nullable = false)
        val model: String,
        @Column(name = "DAILY_FEE", length = 20, nullable = false)
        val dailyFee: BigDecimal,
        @ManyToOne(targetEntity = Location::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "LOCATION_ID", nullable = false)
        val location: Location,
        @OneToOne(mappedBy = "vehicle", cascade = [CascadeType.ALL])
        val vehicleParameters: VehicleParameters
)