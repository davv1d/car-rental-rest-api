package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
@Table(name = "LOCATIONS")
data class Location(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "COUNTRY", length = 40, nullable = false)
        val country: String,
        @Column(name = "CITY", length = 40, nullable = false)
        val city: String,
        @Column(name = "ADDRESS", length = 100, nullable = false)
        val address: String
)