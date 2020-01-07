package com.davv1d.carrental.mapper

import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.RoleDto
import org.springframework.stereotype.Component

@Component
class RoleMapper {
    fun mapToRoleDto(role: Role): RoleDto = RoleDto(role.name)
    fun mapToRoleDtoList(roles: List<Role>): List<RoleDto> =
            roles.asSequence()
                    .map { role: Role -> this.mapToRoleDto(role) }
                    .toList()
}