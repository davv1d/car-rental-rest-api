package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.RoleDto
import org.springframework.stereotype.Component

@Component
class RoleMapper(val privilegeMapper: PrivilegeMapper) {
    fun mapToRoleDto(role: Role): RoleDto = with(role) { RoleDto(name, privilegeMapper.mapToPrivilegeDtoList(privileges)) }
    fun mapToRoleDtoList(roles: List<Role>): List<RoleDto> =
            roles.asSequence()
                    .map { role: Role -> this.mapToRoleDto(role) }
                    .toList()

    fun mapToRole(roleDto: RoleDto): Role {
        return with(roleDto) {
            Role(name = roleName, privileges = privilegeMapper.mapToPrivilegeSet(privileges))
        }
    }
}