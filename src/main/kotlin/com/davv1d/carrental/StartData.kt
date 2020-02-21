package com.davv1d.carrental

import com.davv1d.carrental.constants.*
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.User
import com.davv1d.carrental.service.PrivilegeService
import com.davv1d.carrental.service.RoleService
import com.davv1d.carrental.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class StartData(private val privilegeService: PrivilegeService, private val roleService: RoleService, private val userService: UserService, private val passwordEncoder: PasswordEncoder) {
    private val logger = LoggerFactory.getLogger(StartData::class.java)

    @PostConstruct
    fun createRole() {
        val privilege1 = Privilege(name = USER_REGISTRATION)
        val privilege3 = Privilege(name = GET_PRIVILEGES)
        val privilege4 = Privilege(name = GET_LOCATIONS)
        val privilege5 = Privilege(name = SAVE_LOCATION)
        val privilege6 = Privilege(name = DELETE_LOCATION)
        val privilege7 = Privilege(name = GET_ROLES)
        val privilege8 = Privilege(name = SAVE_ROLE)
        val privilege9 = Privilege(name = UPDATE_OF_ROLE_PRIVILEGES)
        val privilege10 = Privilege(name = DELETE_ROLE)
        val privilege11 = Privilege(name = GET_LOGGED_USER)
        val privilege12 = Privilege(name = GET_USER_BY_NAME)
        val privilege13 = Privilege(name = GET_USER_BY_EMAIL)
        val privilege14 = Privilege(name = GET_USERS)
        val privilege15 = Privilege(name = DELETE_USER)
        val privilege16 = Privilege(name = CHANGE_USER_EMAIL)
        val privilege17 = Privilege(name = SAVE_VEHICLE)
        val privilege18 = Privilege(name = GET_VEHICLES)
        val privilege19 = Privilege(name = GET_VEHICLE_BY_REGISTRATION)
        val privilege20 = Privilege(name = GET_VEHICLES_BY_BRAND)
        val privilege21 = Privilege(name = GET_VEHICLES_BY_FUEL_TYPE)
        val privilege22 = Privilege(name = GET_VEHICLES_BY_LOCATION)

        val allPrivileges = mutableSetOf<Privilege>().apply {
            add(privilege1)
            add(privilege3)
            add(privilege4)
            add(privilege5)
            add(privilege6)
            add(privilege7)
            add(privilege8)
            add(privilege9)
            add(privilege10)
            add(privilege11)
            add(privilege12)
            add(privilege13)
            add(privilege14)
            add(privilege15)
            add(privilege16)
            add(privilege17)
            add(privilege18)
            add(privilege19)
            add(privilege20)
            add(privilege21)
            add(privilege22)
        }

        val customerPrivileges = mutableSetOf<Privilege>().apply {
            add(privilege11)
            add(privilege16)
            add(privilege18)
            add(privilege20)
            add(privilege21)
            add(privilege22)
        }

        privilegeService.saveAll(allPrivileges)

        val resultRoleAdmin = roleService.getRoleByName("admin")
        resultRoleAdmin.forEach(onSuccess = {logger.info("Role admin is exist")}, onFailure = { logger.info("Role admin not exist")})
        val adminRole = when (resultRoleAdmin.isFailure()) {
            true -> roleService.save(Role(name = "admin", privileges = allPrivileges))
            else -> resultRoleAdmin
        }

        val resultRoleCustomer = roleService.getRoleByName("customer")
        resultRoleCustomer.forEach(onSuccess = {logger.info("Role customer is exist")}, onFailure = { logger.info("Role customer not exist")})
        if (resultRoleCustomer.isFailure()) roleService.save(Role(name = "customer", privileges = customerPrivileges))

        val resultUserByName = userService.getUserByName("admin")
        resultUserByName.forEach(onSuccess = {logger.info("User admin is exist")}, onFailure = { logger.info("User admin not exist")})
        val userAdmin = when (resultUserByName.isFailure()) {
            true -> {
                adminRole
                        .map { role ->  User(username = "admin", password = passwordEncoder.encode("admin"), email = "admin@admin.pl", role = role) }
                        .flatMap { user ->  userService.save(user) }
            }
            else -> resultUserByName
        }
        userAdmin.forEach(onSuccess = {logger.info("User admin is exist")}, onFailure = { logger.info("User admin not exist")})
    }

}