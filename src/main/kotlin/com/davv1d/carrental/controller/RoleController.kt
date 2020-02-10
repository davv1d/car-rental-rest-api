package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.RoleDto
import com.davv1d.carrental.fuctionHighLevel.writeAndThrowError
import com.davv1d.carrental.mapper.RoleMapper
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.service.RoleService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class RoleController(private val roleService: RoleService, private val roleMapper: RoleMapper, private val privilegeRepository: PrivilegeRepository) {
    private val logger = LoggerFactory.getLogger(RoleController::class.java)
//    @PostConstruct
//    fun createRole() {
//        val privilege1 = Privilege(name = "test 1")
//        val privilege2 = Privilege(name = "test 2")
//        privilegeRepository.save(privilege1)
//        privilegeRepository.save(privilege2)
//        val userPrivileges = setOf(privilege1)
//        val adminPrivileges = setOf(privilege1, privilege2)
//        val roleUser = Role(name = "user", privileges = userPrivileges)
//        val roleAdmin = Role(name = "admin", privileges = adminPrivileges)
//        roleService.save(roleUser)
//        roleService.save(roleAdmin)
//    }

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
        roleService.updateOfRolePrivileges(roleMapper.mapToRole(roleDto)).
                forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
    }
}