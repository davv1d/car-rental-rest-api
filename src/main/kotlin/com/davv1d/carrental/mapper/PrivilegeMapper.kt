package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.PrivilegeDto
import org.springframework.stereotype.Component

@Component
class PrivilegeMapper {
    fun mapToPrivilegeDto(privilege: Privilege): PrivilegeDto = with(privilege) { PrivilegeDto(name) }
    fun mapToPrivilegeDtoList(privileges: List<Privilege>): List<PrivilegeDto> {
        return privileges.asSequence()
                .map(this::mapToPrivilegeDto)
                .toList()
    }

    fun mapToPrivilege(privilegeDto: PrivilegeDto): Privilege = with(privilegeDto) { Privilege(name = name) }
    fun mapToPrivilegeList(privilegeDtoList: List<PrivilegeDto>): List<Privilege> {
        return privilegeDtoList.asSequence()
                .map(this::mapToPrivilege)
                .toList()
    }
}