package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
@Table(name = "ROLES")
data class Role(
        @Id
        @GeneratedValue
        @Column(name = "ROLE_ID", nullable = false)
        val id: Int = 0,
        @Column(name = "ROLE_NAME", length = 20, unique = true, nullable = false)
        val name: String)