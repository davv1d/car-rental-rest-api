package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
@Table(name = "USERS")
data class User(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "USERNAME", length = 20, unique = true, nullable = false)
        val username: String,
        @Column(name = "PASSWORD", nullable = false)
        val password: String,
        @Column(name = "EMAIL", length = 50, unique = true, nullable = false)
        val email: String,
        @ManyToOne(targetEntity = Role::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "ROLE_ID", nullable = false)
        val role: Role
)