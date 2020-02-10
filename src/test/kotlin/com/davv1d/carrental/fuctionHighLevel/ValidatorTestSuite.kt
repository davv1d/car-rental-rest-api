package com.davv1d.carrental.fuctionHighLevel

import com.davv1d.carrental.domain.*
import com.davv1d.carrental.repository.*
import com.davv1d.carrental.service.PrivilegeService
import com.davv1d.carrental.service.RoleService
import com.davv1d.carrental.service.VehicleService
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.condition.ConditionGenerator
import com.davv1d.carrental.validate.condition.LocationDbConditions
import com.davv1d.carrental.validate.condition.RoleDbConditions
import org.hibernate.exception.ConstraintViolationException
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidatorTestSuite(
        @Autowired val locationRepository: LocationRepository,
        @Autowired val roleDbValidator: ConditionValidator<Role>,
        @Autowired val roleDbConditions: RoleDbConditions,
//        @Autowired val privilegeRepository: PrivilegeRepository,
        @Autowired val roleRepository: RoleRepository,
        @Autowired val privilegeService: PrivilegeService,
        @Autowired val roleService: RoleService,
        @Autowired val vehicleRepository: VehicleRepository,
        @Autowired val privilegeRepository: PrivilegeRepository,
        @Autowired val userRepository: UserRepository,
        @Autowired val vehicleService: VehicleService) {

    @Test
    fun testValid() {
        val location = Location(0, "CITY", "STREET")
        val location2 = Location(0, "CITY2", "STREET2")
        locationRepository.save(location)
        val vehicle = Vehicle(0, "WML", "AUDI", "A6", BigDecimal.ONE, Location(100, "CITY", "STREET"), "SEDAN", 1998, "DIESEL", 110)
        val save = vehicleService.save(vehicle)
        println(save)
//        val vehicle2 = Vehicle(0, "WML", "AUDI", "A6", BigDecimal.ONE, location2, "SEDAN", 1998, "DIESEL", 110)
//        val save2 = vehicleService.save(vehicle2)
//        val role = Role(name = "admin")
//        roleRepository.save(role)
//        val user = User(username = "test1", password = "test1", email = "test1", role = role)
//        userRepository.save(user)
//
//        val findByUsername = userRepository.findByUsername("test1")
//        println(findByUsername)
//        val location = Location(city = "mlawa", street = "warszawska")
//        val location2 = Location(city = "mlawa", street = "warszawska")
//        locationRepository.save(location)
//        locationRepository.save(location2)

//        val vehicleParameters = VehicleParameters(bodyType = "combi", productionYear = 2010, fuelType = "diesel", power = 110)
//        val vehicle = Vehicle(registration = "WML 0001", brand = "citroen", model = "c5", dailyFee = BigDecimal(100), location = location, vehicleParameters = vehicleParameters)
//        vehicleRepository.save(vehicle)
//        val findAll = vehicleRepository.findAll()

//        println(findAll)


//        val location = Location(city = "mlawa", street = "warszawska")
//        locationRepository.save(location)
//        val location1 = Location(city = "MLAWA", street = "warsawska")
//        validator.dbValidate(location1).forEach(onSuccess = { loc ->
//            locationRepository.save(loc)
//            println("")
//        }, onFailure = { println(it.message) })
//
//        val findAll = locationRepository.findAll()
//        println(findAll)
//        with(location) {
//            val findLocation = locationRepository.findLocation(city = "mLAwa", street = "warsZawska")
//            println(findLocation)
//        }
//
//        val findAllByCity = locationRepository.findAllByCity("mlawa")
//        println(findAllByCity.size)


//        val privilege1 = Privilege(name = "test 1")
//        val privilege2 = Privilege(name = "test 2")
//        val privilege3 = Privilege(name = "test 3")
//        val privilege4 = Privilege(name = "test 4")
//
//        privilegeRepository.save(privilege1)
//        privilegeRepository.save(privilege2)
//        privilegeRepository.save(privilege3)
//        val adminPrivileges = setOf(privilege1, privilege2)
//        val role = Role(name = "admin", privileges = adminPrivileges)
//        roleRepository.save(role)
////        val privilegesConditions = roleDbConditions.get(role2)
////        val dbValidate = validator.dbValidate(role2)
//
//        val adminPrivileges2 = setOf(privilege1, privilege2, privilege3)
//        val role2 = Role(name = "admin", privileges = adminPrivileges2)
////        println(roleRepository.findAll().size)
////        println(roleService.save(role2))
////        println(roleRepository.findAll().size)
////        println("")
//
//        println(roleRepository.findAll())
//        val updateRole = roleService.updateRole(role2)
//        println(updateRole)
//        println()
//        println(roleRepository.findAll())
//        val many = privilegeRepository.getMany(listOf("test 1", "test 2"))
//        println(many)
//        println(roleRepository.findAll().size)
//        println(roleService.save2(role2))
//        println(roleRepository.findAll().size)

//        val trueCase = locationRepository.existsByAddressIgnoreCase("warszawska")
//        val trueCaseIgnoreCase = locationRepository.existsByAddressIgnoreCase("warSZAWska")
//        val falseCase = locationRepository.existsByAddressIgnoreCase("warsz")
//        assertTrue(trueCase)
//        assertTrue(trueCaseIgnoreCase)
//        assertFalse(falseCase)
//        println("the same $trueCase | ignore case $trueCaseIgnoreCase | different $falseCase")
    }
}