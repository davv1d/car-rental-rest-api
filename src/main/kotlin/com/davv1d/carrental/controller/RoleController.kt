package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.dto.RoleDto
import com.davv1d.carrental.fuctionHighLevel.writeAndThrowError
import com.davv1d.carrental.mapper.RoleMapper
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.service.RoleService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class RoleController(private val roleService: RoleService, private val roleMapper: RoleMapper) {
    private val logger = LoggerFactory.getLogger(RoleController::class.java)

    @GetMapping(value = ["/role"])
    fun getAllRole(): List<RoleDto> {
        return roleMapper.mapToRoleDtoList(roleService.getAllRole())
    }

    @PostMapping(value = ["/role"])
    fun save(@RequestBody @Valid roleDto: RoleDto) {
        roleService.save(roleMapper.mapToRole(roleDto))
                .forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
    }

    @PutMapping(value = ["/role"])
    fun updateRole(@RequestBody @Valid roleDto: RoleDto) {
        roleService.updateOfRolePrivileges(roleMapper.mapToRole(roleDto)).forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
    }

    @DeleteMapping(value = ["/role"], params = ["id"])
    fun deleteById(id: Int) {
        roleService.deleteById(id)
                .forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
    }
}