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
    lateinit var locationDbConditions: LocationDbConditions
    @Autowired
    lateinit var privilegeDbConditions: PrivilegeDbConditions
    @Autowired
    lateinit var updateRoleConditions: UpdateRoleConditions
    @Autowired
    lateinit var vehicleDbConditions: VehicleDbConditions
    @Autowired
    lateinit var updateVehicleConditions: UpdateVehicleConditions

    @Bean
    fun userValidator(): ConditionValidator<User> = Validator(userDbConditions)

    @Bean
    fun roleDbValidator(): ConditionValidator<Role> = Validator(roleDbConditions)

    @Bean
    fun locationDbValidator(): ConditionValidator<Location> = Validator(locationDbConditions)

    @Bean
    fun privilegeDbValidator(): ConditionValidator<Privilege> = Validator(privilegeDbConditions)

    @Bean
    fun updateRoleValidator(): ConditionValidator<Role> = Validator(updateRoleConditions)

    @Bean
    fun vehicleValidator(): ConditionValidator<Vehicle> = Validator(vehicleDbConditions)

    @Bean
    fun updateVehicleValidator(): ConditionValidator<Vehicle> = Validator(updateVehicleConditions)
}