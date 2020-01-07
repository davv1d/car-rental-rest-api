package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.RoleDto
import com.davv1d.carrental.mapper.RoleMapper
import com.davv1d.carrental.service.RoleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.PostConstruct

@RestController
class RoleController(private val roleService: RoleService, private val roleMapper: RoleMapper) {

//    @PostConstruct
//    fun createRole() {
//        val role = Role(name = "admin")
//        roleService.save(role)
//    }

    @GetMapping(value = ["role"])
    fun getAllRole(): List<RoleDto> {
        return roleMapper.mapToRoleDtoList(roleService.getAllRole())
    }
}