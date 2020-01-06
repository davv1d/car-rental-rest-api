package com.davv1d.carrental.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Role(@Id @GeneratedValue val id: Int = 0, val name: String) {
    override fun toString(): String {
        return "Role(id=$id, name='$name')"
    }
}