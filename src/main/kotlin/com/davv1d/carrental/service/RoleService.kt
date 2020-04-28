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

    fun save(role: Role): Result<Role> = saveOrUpdate(role, roleDbValidator)

    fun updateRole(role: Role): Result<Role> = saveOrUpdate(role, updateRoleValidator)

    fun saveOrUpdate(role: Role, validator: ConditionValidator<Role>): Result<Role> {
        return roleDbValidator.dbValidate(role)
                .map { r -> convert(r, privilegeService::getByNames, privilegeMapper::mapToNameSet) }
                .flatMap(this::secureSave)
    }

    fun convert(role: Role, fetchPrivileges: (Set<String>) -> Set<Privilege>, mapPrivilegeNameToSetString: (Set<Privilege>) -> Set<String>): Role {
        val privileges = fetchPrivileges.invoke(mapPrivilegeNameToSetString.invoke(role.privileges))
        return Role(id = role.id, name = role.name, privileges = privileges)
    }

    fun getAllRole(): List<Role> = roleRepository.findAll()

    fun secureSave(role: Role): Result<Role> = generalService.secureSave(role, roleRepository::save)

    fun deleteById(id: Int): Result<Unit> {
        return removeRoleValidator.dbValidate(id)
                .map { roleRepository.deleteById(it) }
    }
}