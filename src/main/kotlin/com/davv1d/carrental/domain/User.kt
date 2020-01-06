package com.davv1d.carrental.domain

import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue val id: Int = 0,
        val username: String,
        val password: String,
        val email: String,
        @ManyToOne(targetEntity = Role::class, fetch = FetchType.EAGER)
        @JoinColumn(name = "ROLE_ID", nullable = false)
        val role: Role) {

    override fun toString(): String {
        return "User(id=$id, username='$username', password='$password', email='$email')"
    }
}