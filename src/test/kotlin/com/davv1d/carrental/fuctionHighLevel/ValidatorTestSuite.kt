package com.davv1d.carrental.fuctionHighLevel

import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.repository.PrivilegeRepository
import com.davv1d.carrental.repository.RoleRepository
import com.davv1d.carrental.service.PrivilegeService
import com.davv1d.carrental.service.RoleService
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.condition.ConditionGenerator
import com.davv1d.carrental.validate.condition.LocationDbConditions
import com.davv1d.carrental.validate.condition.RoleDbConditions
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidatorTestSuite(
        @Autowired val locationRepository: LocationRepository,
        @Autowired val roleDbValidator: ConditionValidator<Role>,
        @Autowired val roleDbConditions: RoleDbConditions,
        @Autowired val privilegeRepository: PrivilegeRepository,
        @Autowired val roleRepository: RoleRepository,
        @Autowired val privilegeService: PrivilegeService,
        @Autowired val roleService: RoleService) {

    @Test
    fun testValid() {
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


        val privilege1 = Privilege(name = "test 1")
        val privilege2 = Privilege(name = "test 2")
        val privilege3 = Privilege(name = "test 3")
        val privilege4 = Privilege(name = "test 4")

        privilegeRepository.save(privilege1)
        privilegeRepository.save(privilege2)
        privilegeRepository.save(privilege3)
        val adminPrivileges = setOf(privilege1, privilege2)
        val role = Role(name = "admin", privileges = adminPrivileges)
        roleRepository.save(role)
//        val privilegesConditions = roleDbConditions.get(role2)
//        val dbValidate = validator.dbValidate(role2)

        val adminPrivileges2 = setOf(privilege1, privilege2, privilege3)
        val role2 = Role(name = "admin", privileges = adminPrivileges2)
//        println(roleRepository.findAll().size)
//        println(roleService.save(role2))
//        println(roleRepository.findAll().size)
//        println("")

        println(roleRepository.findAll())
        val updateRole = roleService.updateRole(role2)
        println(updateRole)
        println()
        println(roleRepository.findAll())
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