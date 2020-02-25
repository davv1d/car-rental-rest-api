package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class UpdateRoleConditions(private val privilegeRepository: PrivilegeRepository, private val roleRepository: RoleRepository) : ConditionGenerator<Role> {
    override fun get(value: Role): List<Condition<Role>> {
        val roleCondition = Condition(value, "ROLE_NOT_EXIST_IN_DATABASE", { role -> roleRepository.doesRoleNotExistById(role.id) })
        val privilegesConditions = getPrivilegesConditions(value)
        return privilegesConditions + roleCondition
    }

    fun getPrivilegesConditions(role: Role): List<Condition<Role>> {
        return role.privileges.asSequence()
                .map { privilege -> Condition(role, "Privilege " + privilege.name + " is not exist", { privilegeRepository.doesNotExistByName(privilege.name) }) }
                .toList()
    }
}