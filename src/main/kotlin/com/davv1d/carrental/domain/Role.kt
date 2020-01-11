package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
@Table(name = "ROLES")
data class Role(
        @Id
        @GeneratedValue
        @Column(name = "ID", nullable = false)
        val id: Int = 0,
        @Column(name = "NAME", length = 20, unique = true, nullable = false)
        val name: String,
        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "roles_privileges",
                joinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")],
                inverseJoinColumns = [JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID")])
        val privileges: Set<Privilege> = emptySet()
)