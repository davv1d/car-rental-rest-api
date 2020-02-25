package com.davv1d.carrental.validate.configuration

import com.davv1d.carrental.domain.*
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.Validator
import com.davv1d.carrental.validate.condition.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidateConfig {
    @Autowired
    lateinit var userDbConditions: ConditionGenerator<User>
    @Autowired
    lateinit var roleDbConditions: ConditionGenerator<Role>
    @Autowired
    lateinit var locationDbConditions: ConditionGenerator<Location>
    @Autowired
    lateinit var privilegeDbConditions: ConditionGenerator<Privilege>
    @Autowired
    lateinit var updateRoleConditions: ConditionGenerator<Role>
    @Autowired
    lateinit var vehicleDbConditions: ConditionGenerator<Vehicle>
    @Autowired
    lateinit var updateVehicleConditions: ConditionGenerator<Vehicle>
    @Autowired
    lateinit var removeLocationConditions: ConditionGenerator<Int>
    @Autowired
    lateinit var removeRoleConditions: ConditionGenerator<Int>
    @Autowired
    lateinit var vehicleLocationConditions: ConditionGenerator<VehicleLocation>

    @Autowired
    lateinit var rentalSaveDateConditions: ConditionGenerator<Rental>
    @Autowired
    lateinit var rentalDbConditions: ConditionGenerator<Rental>

    @Bean
    fun userValidator(): ConditionValidator<User> = Validator(userDbConditions)

    @Bean
    fun roleDbValidator(): ConditionValidator<Role> = Validator(roleDbConditions)

    @Bean
    fun locationDbValidator(): ConditionValidator<Location> = Validator(locationDbConditions)

    @Bean
    fun removeLocationValidator(): ConditionValidator<Int> = Validator(removeLocationConditions)

    @Bean
    fun privilegeDbValidator(): ConditionValidator<Privilege> = Validator(privilegeDbConditions)

    @Bean
    fun updateRoleValidator(): ConditionValidator<Role> = Validator(updateRoleConditions)

    @Bean
    fun vehicleValidator(): ConditionValidator<Vehicle> = Validator(vehicleDbConditions)

    @Bean
    fun updateVehicleValidator(): ConditionValidator<Vehicle> = Validator(updateVehicleConditions)

    @Bean
    fun removeRoleValidator(): ConditionValidator<Int> = Validator(removeRoleConditions)

    @Bean
    fun vehicleLocationValidator(): ConditionValidator<VehicleLocation> = Validator(vehicleLocationConditions)

    @Bean
    fun rentalSaveValidator(): ConditionValidator<Rental> = Validator(rentalDbConditions)

    @Bean
    fun rentalSaveDateValidator(): ConditionValidator<Rental> = Validator(rentalSaveDateConditions)
}