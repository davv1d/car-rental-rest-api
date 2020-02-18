package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class RemoveRoleConditions(private val roleRepository: RoleRepository) : ConditionGenerator<Int> {
    override fun get(value: Int): List<Condition<Int>> {
        val condition1 = Condition(value, "ROLE WITH THIS ID NOT EXIST", { !roleRepository.findById(it).isPresent })
        val condition2 = Condition(value, "USERS HAVE THIS ROLE", { roleRepository.areThereUsersWithThisRole(it) })
        val condition3 = Condition(value, "ROLE ADMIN CAN NOT BE REMOVE", { isRoleNameAdmin(it) })
        return listOf(condition1, condition2, condition3)
    }

    private fun isRoleNameAdmin(id: Int): Boolean {
        val optionalRole = roleRepository.findById(id)
        return when (optionalRole.isPresent) {
            true -> optionalRole.get().name.equals(other = "ADMIN", ignoreCase = true)
            else -> false
        }
    }
}