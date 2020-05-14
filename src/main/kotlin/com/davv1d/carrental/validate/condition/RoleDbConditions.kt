package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.constants.ROLE_EXIST_IN_DATABASE
import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.repository.RoleRepository
import org.springframework.stereotype.Component

@Component
class RoleDbConditions(
        private val roleRepository: RoleRepository,
        private val privilegeRepository: PrivilegeRepository
) {

    fun fetchRoleSaveConditions(): List<Condition<Role>> {
        val roleCondition = Condition<Role>(ROLE_EXIST_IN_DATABASE) { role -> roleRepository.doesRoleNotExistByName(role.name) }
        val privilegesConditions = fetchPrivilegesExistConditions()
        return listOf(roleCondition, privilegesConditions)
    }

    fun fetchRoleUpdateConditions(): List<Condition<Role>> {
        val idCondition = Condition<Role>("ID DOES NOT EXIST") { role -> roleRepository.existsById(role.id) }
        val nameCondition = Condition<Role>("NAME EXISTS") { role -> roleRepository.doesNameNotExistOrBelongToTheUserWithThisId(role.name, role.id) }
        val privilegesConditions = fetchPrivilegesExistConditions()
        return listOf(idCondition, nameCondition, privilegesConditions)
    }

    fun fetchRoleRemoveConditions(): List<Condition<Int>> {
        val condition1 = Condition<Int>("ROLE WITH THIS ID NOT EXIST") { id -> roleRepository.existsById(id) }
        val condition2 = Condition<Int>("USERS HAVE THIS ROLE") { id -> roleRepository.doUsersNotHaveRoleWithThisId(id) }
        val condition3 = Condition<Int>("ROLE ADMIN CAN NOT BE REMOVE") { id -> isRoleNameNotAdmin(id) }
        return listOf(condition1, condition2, condition3)
    }

    private fun isRoleNameNotAdmin(id: Int): Boolean {
        val optionalRole = roleRepository.findById(id)
        return when (optionalRole.isPresent) {
            true -> !optionalRole.get().name.equals(other = "ADMIN", ignoreCase = true)
            else -> true
        }
    }

    private fun fetchPrivilegesExistConditions(): Condition<Role> {
        return Condition("Not all privileges exist") { role -> doPrivilegesExist(role.privileges) }
    }

    private fun doPrivilegesExist(privileges: Set<Privilege>): Boolean {
        val names = privileges.map { privilege -> privilege.name }.toSet()
        val fetchedPrivileges = privilegeRepository.findPrivilegesByNames(names)
        return privileges.size == fetchedPrivileges.size
    }
}