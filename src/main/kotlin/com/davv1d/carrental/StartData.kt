package com.davv1d.carrental

import com.davv1d.carrental.constants.*
import com.davv1d.carrental.domain.*
import com.davv1d.carrental.service.*
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.annotation.PostConstruct
import com.davv1d.carrental.constants.AdminAccountConfig.email
import com.davv1d.carrental.constants.AdminAccountConfig.name
import com.davv1d.carrental.constants.AdminAccountConfig.password
import com.davv1d.carrental.repository.PrivilegeRepository
import java.util.stream.Collectors

@Component
class StartData(private val roleService: RoleService,
                private val userService: UserService,
                private val passwordEncoder: PasswordEncoder,
                private val privilegeRepository: PrivilegeRepository) {
    private val logger = LoggerFactory.getLogger(StartData::class.java)

    @PostConstruct
    fun createRole() {
        val privilege1 = Privilege(name = USER_REGISTRATION)
        val privilege2 = Privilege(name = GET_PRIVILEGES)
        val privilege3 = Privilege(name = GET_LOCATIONS)
        val privilege4 = Privilege(name = SAVE_LOCATION)
        val privilege5 = Privilege(name = DELETE_LOCATION)
        val privilege6 = Privilege(name = GET_ROLES)
        val privilege7 = Privilege(name = SAVE_ROLE)
        val privilege8 = Privilege(name = UPDATE_OF_ROLE_PRIVILEGES)
        val privilege9 = Privilege(name = DELETE_ROLE)
        val privilege10 = Privilege(name = GET_LOGGED_USER)
        val privilege11 = Privilege(name = GET_USER_BY_NAME)
        val privilege12 = Privilege(name = GET_USER_BY_EMAIL)
        val privilege13 = Privilege(name = GET_USERS)
        val privilege14 = Privilege(name = DELETE_USER)
        val privilege15 = Privilege(name = CHANGE_USER_EMAIL)
        val privilege16 = Privilege(name = SAVE_VEHICLE)
        val privilege17 = Privilege(name = GET_VEHICLES)
        val privilege18 = Privilege(name = GET_VEHICLE_BY_REGISTRATION)
        val privilege19 = Privilege(name = GET_VEHICLES_BY_BRAND)
        val privilege20 = Privilege(name = GET_VEHICLES_BY_FUEL_TYPE)
        val privilege21 = Privilege(name = GET_VEHICLES_BY_LOCATION)

        var allPrivileges = mutableSetOf<Privilege>().apply {
            add(privilege1)
            add(privilege2)
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
        }

        var customerPrivileges = mutableListOf<Privilege>().apply {
            add(privilege10)
            add(privilege15)
            add(privilege17)
            add(privilege19)
            add(privilege20)
            add(privilege21)
        }
        if (privilegeRepository.findAll().isNotEmpty()) {
           allPrivileges = privilegeRepository.findAll() as MutableSet<Privilege>
            val customerNamePrivileges = customerPrivileges.stream().map { t -> t.name }.collect(Collectors.toSet())
            val customerPrivilegesId = allPrivileges.stream()
                    .filter { t -> customerNamePrivileges.contains(t.name) }
                    .map(Privilege::id)
                    .collect(Collectors.toSet())
            customerPrivileges = privilegeRepository.findAllById(customerPrivilegesId) as MutableList<Privilege>
        } else {
            privilegeRepository.saveAll(allPrivileges)
        }

        val resultRoleAdmin = roleService.getRoleByName("admin")
        resultRoleAdmin.forEach(onSuccess = { logger.info("Role admin is exist") }, onFailure = { logger.info("Role admin not exist") })
        val adminRole = when (resultRoleAdmin.isFailure()) {
            true -> roleService.save(Role(name = "admin", privileges = allPrivileges))
            else -> resultRoleAdmin
        }

        val resultRoleCustomer = roleService.getRoleByName("customer")
        resultRoleCustomer.forEach(onSuccess = { logger.info("Role customer is exist") }, onFailure = { logger.info("Role customer not exist") })
        if (resultRoleCustomer.isFailure()) roleService.save(Role(name = "customer", privileges = customerPrivileges.toSet()))

        val resultUserByName = userService.getUserByName("admin")
        resultUserByName.forEach(onSuccess = { logger.info("User admin is exist") }, onFailure = { logger.info("User admin not exist") })
        val userAdmin = when (resultUserByName.isFailure()) {
            true -> {
                adminRole
                        .map { role -> User(username = name, password = passwordEncoder.encode(password), email = email, role = role, active = true) }
                        .flatMap { user -> userService.save(user) }
            }
            else -> resultUserByName
        }
        userAdmin.forEach(onSuccess = { logger.info("User admin is exist") }, onFailure = { logger.info("User admin not exist") })

        println(LocalDateTime.now())

//        val location1 = Location(0, "city1", "street1")
//        val location2 = Location(0, "city2", "street2")
//        val savedLocation1 = locationRepository.save(location1)
//        val savedLocation2 = locationRepository.save(location2)
//
//        val vehicle1 = Vehicle(0, "WML 1", "AUDI", "A6", BigDecimal.ZERO, "SEDAN", 1998, "DIESEL")
//        val vehicle2 = Vehicle(0, "WML 2", "AUDI", "A4", BigDecimal.TEN, "COMBI", 2000, "DIESEL")
//        val vehicle3 = Vehicle(0, "WML 3", "CITROEN", "C5", BigDecimal.ZERO, "SEDAN", 2010, "GAS")
//        val savedVehicle1 = vehicleRepository.save(vehicle1)
//        val savedVehicle2 = vehicleRepository.save(vehicle2)
//        val savedVehicle3 = vehicleRepository.save(vehicle3)
//
//        val vehicleLocation1 = VehicleLocation(0, LocalDateTime.now(), savedLocation1, savedVehicle1)
//        val vehicleLocation2 = VehicleLocation(0, LocalDateTime.now().plusDays(2), savedLocation1, savedVehicle2)
//        val vehicleLocation3 = VehicleLocation(0, LocalDateTime.now().plusDays(4), savedLocation2, savedVehicle3)
//        vehicleLocationRepository.save(vehicleLocation1)
//        vehicleLocationRepository.save(vehicleLocation2)
//        vehicleLocationRepository.save(vehicleLocation3)
    }
}