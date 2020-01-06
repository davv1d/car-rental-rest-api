package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.constants.ROLE_EXIST_IN_DATABASE
import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class RoleDbConditions(val roleRepository: RoleRepository) : ConditionGenerator<Role> {

    override fun get(value: Role): List<Condition<Role>> {
        val ifStatement = Condition(value, ROLE_EXIST_IN_DATABASE, checkFunction = { role -> roleRepository.existsByName(role.name) })
        return listOf(ifStatement)
    }

}