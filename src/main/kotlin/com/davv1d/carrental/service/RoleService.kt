package com.davv1d.carrental.service

import com.davv1d.carrental.constants.ROLE_WITH_THIS_NAME_IS_NOT_EXIST
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.mapper.PrivilegeMapper
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.RoleRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service

@Service
class RoleService(
        private val roleRepository: RoleRepository,
        private val roleDbValidator: ConditionValidator<Role>,
        private val generalService: GeneralService,
        private val privilegeService: PrivilegeService,
        private val privilegeMapper: PrivilegeMapper,
        private val updateRoleValidator: ConditionValidator<Role>) {

    fun getRoleByName(name: String): Result<Role> = generalService.getByValue(
            value = name,
            error = NotFoundElementException(ROLE_WITH_THIS_NAME_IS_NOT_EXIST),
            function = roleRepository::findByName)

    fun save(role: Role): Result<Role> {
        return roleDbValidator.dbValidate(role)
                .map { r -> getPrivilegesFromDb(r, r.privileges) }
                .flatMap(this::secureSave)
    }

    private fun getPrivilegesFromDb(role: Role, privileges: Set<Privilege>): Role {
        val newPrivileges = privilegeService.getByNames(privilegeMapper.mapToNameSet(privileges))
        return Role(id = role.id, name = role.name, privileges = newPrivileges)
    }


    fun updateRole(role: Role): Result<Role> {
        return updateRoleValidator.dbValidate(role)
                .flatMap { r -> getRoleByName(r.name) }
                .map { r -> getPrivilegesFromDb(r, role.privileges) }
                .flatMap(this::secureSave)
    }

    fun getAllRole(): List<Role> = roleRepository.findAll()

    fun secureSave(role: Role): Result<Role> = generalService.secureSave(role, roleRepository::save)
}