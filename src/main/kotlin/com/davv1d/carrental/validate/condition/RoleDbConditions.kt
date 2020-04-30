package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.constants.ROLE_EXIST_IN_DATABASE
import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class RoleDbConditions(val roleRepository: RoleRepository, private val privilegeRepository: PrivilegeRepository) : ConditionGenerator<Role> {

    override fun get(): List<Condition<Role>> {
        val roleCondition = Condition<Role>( ROLE_EXIST_IN_DATABASE) { role -> roleRepository.existsByName(role.name) }
        val privilegesConditions = Condition<Role>("Not all privileges exist") { role -> checkPrivileges(role.privileges) }
        return listOf(roleCondition, privilegesConditions)
    }

    private fun checkPrivileges(privileges: Set<Privilege>): Boolean {
        val names = privileges.map { privilege -> privilege.name }.toSet()
        val fetchedPrivileges = privilegeRepository.getMany(names)
        return privileges.size == fetchedPrivileges.size
    }
}