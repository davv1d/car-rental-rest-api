package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class UpdateRoleConditions(private val privilegeRepository: PrivilegeRepository, private val roleRepository: RoleRepository) : ConditionGenerator<Role> {
    override fun get(value: Role): List<Condition<Role>> {
        val idCondition = Condition(value, "ID DOES NOT EXIST", { role -> roleRepository.doesRoleNotExistById(role.id) })
        val nameCondition = Condition(value, "NAME EXISTS", {role -> roleRepository.updateCheckDoesRoleNameExist(role.name, role.id) })
        val privilegesConditions = getPrivilegesConditions(value)
        return privilegesConditions + idCondition + nameCondition
    }

    fun getPrivilegesConditions(role: Role): List<Condition<Role>> {
        return role.privileges.asSequence()
                .map { privilege -> Condition(role, "Privilege " + privilege.name + " is not exist", { privilegeRepository.doesNotExistByName(privilege.name) }) }
                .toList()
    }
}