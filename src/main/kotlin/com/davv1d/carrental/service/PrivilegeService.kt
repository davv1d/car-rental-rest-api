package com.davv1d.carrental.service


import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service

@Service
class PrivilegeService(
        private val privilegeRepository: PrivilegeRepository,
        private val privilegeDbValidator: ConditionValidator<Privilege>,
        private val generalService: GeneralService
) {
    fun getAllPrivileges(): Set<Privilege> = privilegeRepository.findAll()
    fun secureSave(privilege: Privilege): Result<Privilege> = generalService.secureSave(privilege, privilegeRepository::save)
    fun getByNames(names: Set<String>): Set<Privilege> = privilegeRepository.getMany(names)
    fun saveWithValidation(privilege: Privilege): Result<Privilege> {
        return privilegeDbValidator.valid(privilege, ::RuntimeException)
                .flatMap { secureSave(it) }
    }

    fun saveAll(privileges: MutableSet<Privilege>) {
        for (privilege in privileges) {
            saveWithValidation(privilege)
        }
    }
}