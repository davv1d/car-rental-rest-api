package com.davv1d.carrental.fuctionHighLevel

import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.repository.LocationRepository
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.condition.LocationDbConditions
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidatorTestSuite(@Autowired val locationRepository: LocationRepository, @Autowired val validator: ConditionValidator<Location>) {

    @Test
    fun testValid() {
        val location = Location(city = "mlawa", street = "warszawska")
        locationRepository.save(location)
        val location1 = Location(city = "MLAWA", street = "warsawska")
        validator.dbValidate(location1).forEach(onSuccess = { loc ->
            locationRepository.save(loc)
            println("")
        }, onFailure = { println(it.message) })

        val findAll = locationRepository.findAll()
        println(findAll)
        with(location) {
            val findLocation = locationRepository.findLocation(city = "mLAwa", street = "warsZawska")
            println(findLocation)
        }

        val findAllByCity = locationRepository.findAllByCity("mlawa")
        println(findAllByCity.size)

//        val trueCase = locationRepository.existsByAddressIgnoreCase("warszawska")
//        val trueCaseIgnoreCase = locationRepository.existsByAddressIgnoreCase("warSZAWska")
//        val falseCase = locationRepository.existsByAddressIgnoreCase("warsz")
//        assertTrue(trueCase)
//        assertTrue(trueCaseIgnoreCase)
//        assertFalse(falseCase)
//        println("the same $trueCase | ignore case $trueCaseIgnoreCase | different $falseCase")
    }
}