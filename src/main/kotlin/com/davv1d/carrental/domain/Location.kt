package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
@Table(name = "LOCATIONS")
data class Location(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "CITY", length = 40, nullable = false)
        val city: String,
        @Column(name = "STREET", length = 100, nullable = false)
        val street: String
)