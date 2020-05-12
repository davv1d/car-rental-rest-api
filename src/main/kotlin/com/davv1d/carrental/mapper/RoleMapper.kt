package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.dto.RoleDto
import org.springframework.stereotype.Component

@Component
class RoleMapper(val privilegeMapper: PrivilegeMapper) {
    fun mapToRoleDto(role: Role): RoleDto = with(role) { RoleDto(id, name, privilegeMapper.mapToPrivilegeDtoList(privileges)) }
    fun mapToRoleDtoList(roles: List<Role>): List<RoleDto> =
            roles.asSequence()
                    .map { role: Role -> this.mapToRoleDto(role) }
                    .toList()

    fun mapToRoleToSave(roleDto: RoleDto): Role {
        return with(roleDto) {
            Role(0, roleName, privilegeMapper.mapToPrivilegeSet(privileges))
        }
    }

    fun mapToRole(roleDto: RoleDto): Role {
        return with(roleDto) {
            Role(id, roleName, privilegeMapper.mapToPrivilegeSet(privileges))
        }
    }
}