package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
@Table(name = "PRIVILEGES")
data class Privilege(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "NAME", length = 50, unique = true, nullable = false)
        val name: String
)
