package com.davv1d.carrental.service

import com.davv1d.carrental.constants.ROLE_WITH_THIS_NAME_IS_NOT_EXIST
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.RoleRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service

@Service
class RoleService(
        private val roleRepository: RoleRepository,
        private val roleValidator: ConditionValidator<Role>,
        private val generalService: GeneralService) {

    fun getRoleByName(name: String): Result<Role> = generalService.getByValue(
            value = name,
            error = NotFoundElementException(ROLE_WITH_THIS_NAME_IS_NOT_EXIST),
            function = roleRepository::findByName)

    fun save(role: Role): Result<Role> = roleValidator.dbValidate(role).flatMap(this::secureSave)

    fun getAllRole(): List<Role> = roleRepository.findAll()

    fun secureSave(role: Role): Result<Role> = generalService.secureSave(role, roleRepository::save)
}