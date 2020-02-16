package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.dto.PrivilegeDto
import org.springframework.stereotype.Component

@Component
class PrivilegeMapper {
    fun mapToPrivilegeDto(privilege: Privilege): PrivilegeDto = with(privilege) { PrivilegeDto(name) }
    fun mapToPrivilegeDtoList(privileges: Set<Privilege>): List<PrivilegeDto> {
        return privileges.asSequence()
                .map(this::mapToPrivilegeDto)
                .toList()
    }

    fun mapToPrivilege(privilegeDto: PrivilegeDto): Privilege = with(privilegeDto) { Privilege(name = name) }
    fun mapToPrivilegeSet(privilegeDtoList: List<PrivilegeDto>): Set<Privilege> {
        return privilegeDtoList.asSequence()
                .map(this::mapToPrivilege)
                .toSet()
    }

    fun mapToNameSet(privileges: Set<Privilege>): Set<String> {
        return privileges.asSequence()
                .map { privilege -> privilege.name }
                .toSet()
    }
}