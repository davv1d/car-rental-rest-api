package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.repository.PrivilegeRepository
import org.springframework.stereotype.Component

@Component
class PrivilegeDbConditions(private val privilegeRepository: PrivilegeRepository) : ConditionGenerator<Privilege> {
    override fun get(value: Privilege): List<Condition<Privilege>> {
        val con1 = Condition(value, "Privilege " + value.name + " is exist", { p -> privilegeRepository.existsByName(p.name) })
        return listOf(con1)
    }
}