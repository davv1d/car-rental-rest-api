package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.RoleDto
import com.davv1d.carrental.mapper.RoleMapper
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.service.RoleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoleController(private val roleService: RoleService, private val roleMapper: RoleMapper, private val privilegeRepository: PrivilegeRepository) {

//    @PostConstruct
//    fun createRole() {
//        val privilege1 = Privilege(name = "test 1")
//        val privilege2 = Privilege(name = "test 2")
//        privilegeRepository.save(privilege1)
//        privilegeRepository.save(privilege2)
//        val userPrivileges = listOf(privilege1)
//        val adminPrivileges = listOf(privilege1, privilege2)
//        val roleUser = Role(name = "user", privileges = userPrivileges)
//        val roleAdmin = Role(name = "admin", privileges = adminPrivileges)
//        roleService.save(roleUser)
//        roleService.save(roleAdmin)
//    }

    @GetMapping(value = ["/role"])
    fun getAllRole(): List<RoleDto> {
        return roleMapper.mapToRoleDtoList(roleService.getAllRole())
    }
}