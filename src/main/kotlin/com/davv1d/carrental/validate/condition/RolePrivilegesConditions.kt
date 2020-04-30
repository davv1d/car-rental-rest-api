package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.PrivilegeRepository
import org.springframework.stereotype.Component

@Component
class RolePrivilegesConditions(private val privilegeRepository: PrivilegeRepository) {

    fun get(): Condition<Role> {
        return Condition("Not all privileges exist") { role -> checkPrivileges(role.privileges) }
    }

    private fun checkPrivileges(privileges: Set<Privilege>): Boolean {
        val names = privileges.map { privilege -> privilege.name }.toSet()
        val fetchedPrivileges = privilegeRepository.getMany(names)
        return privileges.size == fetchedPrivileges.size
    }
}