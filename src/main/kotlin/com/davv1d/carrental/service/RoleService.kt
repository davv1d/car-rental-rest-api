package com.davv1d.carrental.service

import com.davv1d.carrental.constants.ROLE_WITH_THIS_NAME_IS_NOT_EXIST
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
        private val updateRoleValidator: ConditionValidator<Role>,
        private val removeRoleValidator: ConditionValidator<Int>) {

    fun getRoleByName(name: String): Result<Role> = generalService.getByValue(
            value = name,
            error = NotFoundElementException(ROLE_WITH_THIS_NAME_IS_NOT_EXIST),
            function = roleRepository::findByName)

    fun getRoleById(id: Int): Result<Role> = generalService.getByValue(
            value = id,
            error = NotFoundElementException("ROLE_WITH_THIS_ID_IS_NOT_EXIST"),
            function = roleRepository::findById)

    fun save(role: Role): Result<Role> {
        return roleDbValidator.dbValidate(role)
                .map { r -> privilegeService.getByNames(privilegeMapper.mapToNameSet(r.privileges)) }
                .map { p -> Role(name = role.name, privileges = p) }
                .flatMap(this::secureSave)
    }

    fun updateOfRolePrivileges(role: Role): Result<Role> {
        return updateRoleValidator.dbValidate(role)
                .flatMap { r ->
                    getRoleById(r.id).map { roleDownloaded ->
                        val privilegeDownloaded = privilegeService.getByNames(privilegeMapper.mapToNameSet(r.privileges))
                        Role(roleDownloaded.id, roleDownloaded.name, privilegeDownloaded)
                    }
                }
                .flatMap(this::secureSave)
    }

    fun getAllRole(): List<Role> = roleRepository.findAll()

    fun secureSave(role: Role): Result<Role> = generalService.secureSave(role, roleRepository::save)

    fun deleteById(id: Int): Result<Unit> {
        return removeRoleValidator.dbValidate(id)
                .map { roleRepository.deleteById(it) }
    }
}