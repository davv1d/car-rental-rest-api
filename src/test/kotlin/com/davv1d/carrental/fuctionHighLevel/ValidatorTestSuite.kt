package com.davv1d.carrental.fuctionHighLevel

import com.davv1d.carrental.domain.*
import com.davv1d.carrental.mapper.VehicleLocationMapper
import com.davv1d.carrental.repository.*
import com.davv1d.carrental.service.*
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.condition.RoleDbConditions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidatorTestSuite(
        @Autowired val locationRepository: LocationRepository,
        @Autowired val locationService: LocationService,
        @Autowired val roleDbValidator: ConditionValidator<Role>,
        @Autowired val roleDbConditions: RoleDbConditions,
        @Autowired val roleRepository: RoleRepository,
        @Autowired val privilegeService: PrivilegeService,
        @Autowired val roleService: RoleService,
        @Autowired val vehicleRepository: VehicleRepository,
        @Autowired val privilegeRepository: PrivilegeRepository,
        @Autowired val userRepository: UserRepository,
        @Autowired val vehicleService: VehicleService,
        @Autowired val updateVehicleValidator: ConditionValidator<Vehicle>,
        @Autowired val vehicleLocationRepository: VehicleLocationRepository,
        @Autowired val rentalRepository: RentalRepository,
        @Autowired val vehicleLocationMapper: VehicleLocationMapper,
        @Autowired val rentalService: RentalService) {

    @Test
    fun testValid() {

        val privilege1 = Privilege(name = "test 1")
        privilegeRepository.save(privilege1)
        val adminPrivileges = setOf(privilege1)
        val role = Role(name = "admin1", privileges = adminPrivileges)
        val savedRoleAdmin = roleRepository.save(role)
        val user = User(username = "name", password = "password", email = "email@test.pl", role = savedRoleAdmin)
        val savedUser = userRepository.save(user)

        val location = Location(0, "CITY", "STREET")
        val savedLocation1 = locationRepository.save(location)


        val location2 = Location(0, "CITY2", "STREET2")
        val savedLocation2 = locationRepository.save(location2)

        val vehicle1 = Vehicle(0, "WML 1", "AUDI", "A6", BigDecimal.ONE, "SEDAN", 1998, "DIESEL", 110)
        val savedVehicle = vehicleRepository.save(vehicle1)

        val vehicle2 = Vehicle(0, "WML 2", "CITROEN", "C5", BigDecimal.ONE, "COMBI", 2006, "DIESEL", 220)
        val savedVehicle2 = vehicleRepository.save(vehicle2)

        val vehicleLocation = VehicleLocation(0, LocalDateTime.now(), savedLocation1, savedVehicle)
        val vehicleLocation2 = VehicleLocation(0, LocalDateTime.now().plusDays(6), savedLocation1, savedVehicle)
        val vehicleLocation3 = VehicleLocation(0, LocalDateTime.now().plusDays(4), savedLocation1, savedVehicle)

        val vehicleLocation4 = VehicleLocation(0, LocalDateTime.now().plusDays(4), savedLocation1, savedVehicle2)
        val vehicleLocation5 = VehicleLocation(0, LocalDateTime.now().plusDays(6), savedLocation2, savedVehicle2)

        vehicleLocationRepository.save(vehicleLocation3)
        vehicleLocationRepository.save(vehicleLocation2)
        vehicleLocationRepository.save(vehicleLocation4)
        vehicleLocationRepository.save(vehicleLocation)
        vehicleLocationRepository.save(vehicleLocation5)

        val vehicleWrongId = Vehicle(155, "WML 1", "AUDI", "A6", BigDecimal.ONE, "SEDAN", 1998, "DIESEL", 110)
        val locationWrongId = Location(155, "CITY", "STREET")
        val userWrongName = User(username = "name155", password = "password", email = "email@test.pl", role = savedRoleAdmin)
        val rental = Rental(0, LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(15), vehicleWrongId, userWrongName, locationWrongId, locationWrongId)
        println(rentalService.save(rental))
//        val savedRental = rentalRepository.save(rental)

        vehicleRepository.findVehiclesByLocation(LocalDateTime.now().plusDays(5), 28)
                .forEach{t -> println(t) }


        println(rentalRepository.findAll().size)
        vehicleRepository.findAvailableVehicles(LocalDateTime.now().plusDays(10), LocalDateTime.now().plusDays(11).plusHours(23), 28)
                .forEach(::println)

        println()
        vehicleLocationMapper.mapToVehicleLocationDtoList(vehicleLocationRepository.findVehicleLocationsToSpecificDateByVehicleId(LocalDateTime.now().plusDays(5), vehicle2.id))
                .forEach(::println)

        println(vehicleRepository.doesVehicleNotExistInAvailableVehicles(LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(11), savedLocation1.id, savedVehicle.id))

//        println()
//        println(vehicleRepository.findVehiclesInLocation(LocalDateTime.now().plusDays(7), savedLocation1.id))


//        val location2 = Location(0, "CITY2", "STREET2")
//        val savedLocation2 = locationRepository.save(location2)
//        val vehicle = Vehicle(0, "WML 1", "AUDI", "A6", BigDecimal.ONE, location, "SEDAN", 1998, "DIESEL", 110)
//        val save = vehicleService.save(vehicle)
//        println(save)
////        val findByLocation = vehicleRepository.findByLocation("city", "Street")
////        println(findByLocation)
//        val vehicle2 = Vehicle(0, "WML 2", "AUDI", "A6", BigDecimal.ONE, location, "SEDAN", 1998, "DIESEL", 110)
//        val save2 = vehicleService.save(vehicle2)
//        println(save2)
//        println(vehicleRepository.findAll().size)
//
//        println(vehicleRepository.doesRegistrationExistUpdateVehicle("WML 1", 2))
//        println(vehicleRepository.doesRegistrationExistUpdateVehicle("WML 3", 2))
//        println(vehicleRepository.doesRegistrationExistUpdateVehicle("WML 2", 2))
//
//        val vehicle1 = Vehicle(2, "WML 1", "AUDI", "A6", BigDecimal.ONE, location, "SEDAN", 1998, "DIESEL", 110)
//        println(updateVehicleValidator.dbValidate(vehicle1))
//
//        with(save.getOrElse(vehicle)) {
//            val vehicleUpdate = Vehicle(165, registration, "VOLVO", "V50", BigDecimal.TEN, location, "COMBI", 2006, "GAZ", 220)
//            println(vehicleService.updateVehicle(vehicleUpdate))
//        }
//
//        println(vehicleService.getAll().size)
//
//        val isVehicles = locationRepository.areThereVehiclesInLocation(105)
//        println("is vehicles $isVehicles")
////        locationRepository.deleteById(105)
//        println(locationService.deleteLocationById(savedLocation2.id))
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
//        val userPrivileges = setOf(privilege3)
//        val role = Role(name = "admin", privileges = adminPrivileges)
//        val role1 = Role(name = "user", privileges = userPrivileges)
//        val savedRoleAdmin = roleRepository.save(role)
//        val savedRoleUser = roleRepository.save(role1)
//        val user = User(username = "name", password = "password", email = "email@test.pl", role = savedRoleAdmin)
//        userRepository.save(user)
//        println(savedRoleAdmin)
//        println()
//        println(roleService.deleteById(15))
//        println(roleService.deleteById(savedRoleAdmin.id))
//        println(roleService.deleteById(savedRoleUser.id))

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