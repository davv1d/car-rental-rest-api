package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class UpdateRoleConditions(private val privilegeRepository: PrivilegeRepository, private val roleRepository: RoleRepository) : ConditionGenerator<Role> {
    override fun get(): List<Condition<Role>> {
        val idCondition = Condition<Role>("ID DOES NOT EXIST") { role -> roleRepository.doesRoleNotExistById(role.id) }
        val nameCondition = Condition<Role>("NAME EXISTS") { role -> roleRepository.updateCheckDoesRoleNameExist(role.name, role.id) }
        val privilegesConditions = Condition<Role>("Not all privileges exist") { role -> checkPrivileges(role.privileges)}
        return listOf(idCondition, nameCondition, privilegesConditions)
    }

    private fun checkPrivileges(privileges: Set<Privilege>): Boolean {
        val names = privileges.map { privilege -> privilege.name }.toSet()
        val fetchedPrivileges = privilegeRepository.getMany(names)
        return privileges.size == fetchedPrivileges.size
    }
}