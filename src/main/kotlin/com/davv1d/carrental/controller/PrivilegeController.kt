package com.davv1d.carrental.controller

import com.davv1d.carrental.domain.dto.PrivilegeDto
import com.davv1d.carrental.mapper.PrivilegeMapper
import com.davv1d.carrental.service.PrivilegeService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PrivilegeController(private val privilegeMapper: PrivilegeMapper, private val privilegeService: PrivilegeService) {
    private val logger = LoggerFactory.getLogger(PrivilegeController::class.java)

    @GetMapping(value = ["/privilege"])
    fun getAllPrivileges(): List<PrivilegeDto> {
        return privilegeMapper.mapToPrivilegeDtoList(privilegeService.getAllPrivileges())
    }
//
//    @PostMapping(value = ["/privilege"])
//    fun save(@RequestBody @Valid privilegeDto: PrivilegeDto) {
//        privilegeService.saveWithValidate(privilegeMapper.mapToPrivilege(privilegeDto))
//                .forEach(onSuccess = ::println, onFailure = { writeAndThrowError(logger, it) })
//    }
}