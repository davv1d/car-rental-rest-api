package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.constants.ROLE_EXIST_IN_DATABASE
import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class RoleDbConditions(val roleRepository: RoleRepository, private val privilegeRepository: PrivilegeRepository) : ConditionGenerator<Role> {

    override fun get(value: Role): List<Condition<Role>> {
        val roleCondition = Condition(value, ROLE_EXIST_IN_DATABASE, { role -> roleRepository.existsByName(role.name) })
        val privilegesConditions = getPrivilegesConditions(value)
        return privilegesConditions + roleCondition
    }

    fun getPrivilegesConditions(role: Role): List<Condition<Role>> {
        return role.privileges.asSequence()
                .map { privilege -> Condition(role, """Privilege ${privilege.name} is not exist""", { privilegeRepository.doesNotExistByName(privilege.name) }) }
                .toList()
    }
}