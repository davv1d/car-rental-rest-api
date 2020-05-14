package com.davv1d.carrental.validate.configuration

import com.davv1d.carrental.domain.*
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.Validator
import com.davv1d.carrental.validate.condition.*
import com.davv1d.carrental.validate.condition.LocationDbConditions
import com.davv1d.carrental.validate.condition.PrivilegeDbConditions
import com.davv1d.carrental.validate.condition.RoleDbConditions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidateConfig {
    @Autowired
    lateinit var roleDbConditions: RoleDbConditions
    @Autowired
    lateinit var userDbConditions: UserDbConditions
    @Autowired
    lateinit var locationDbConditions: LocationDbConditions
    @Autowired
    lateinit var vehicleDbConditions: VehicleDbConditions
    @Autowired
    lateinit var privilegeDbConditions: PrivilegeDbConditions
    @Autowired
    lateinit var vehicleLocationConditions: VehicleLocationConditions
    @Autowired
    lateinit var rentalDbConditions: RentalDbConditions

    @Bean
    fun roleDbValidator(): ConditionValidator<Role> = Validator(roleDbConditions::fetchRoleSaveConditions)

    @Bean
    fun updateRoleValidator(): ConditionValidator<Role> = Validator(roleDbConditions::fetchRoleUpdateConditions)

    @Bean
    fun removeRoleValidator(): ConditionValidator<Int> = Validator(roleDbConditions::fetchRoleRemoveConditions)

    @Bean
    fun userValidator(): ConditionValidator<User> = Validator(userDbConditions::fetchUserSaveConditions)

    @Bean
    fun locationDbValidator(): ConditionValidator<Location> = Validator(locationDbConditions::fetchLocationSaveConditions)

    @Bean
    fun removeLocationValidator(): ConditionValidator<Int> = Validator(locationDbConditions::fetchLocationRemoveConditions)

    @Bean
    fun vehicleValidator(): ConditionValidator<Vehicle> = Validator(vehicleDbConditions::fetchVehicleSaveConditions)

    @Bean
    fun vehicleRemovalValidator(): ConditionValidator<Int> = Validator(vehicleDbConditions::fetchVehicleRemoveConditions)

    @Bean
    fun updateVehicleValidator(): ConditionValidator<Vehicle> = Validator(vehicleDbConditions::fetchVehicleUpdateConditions)

    @Bean
    fun privilegeDbValidator(): ConditionValidator<Privilege> = Validator(privilegeDbConditions::fetchPrivilegeSaveConditions)

    @Bean
    fun vehicleLocationValidator(): ConditionValidator<VehicleLocation> = Validator(vehicleLocationConditions::fetchVehicleLocationSaveConditions)

    @Bean
    fun rentalSaveValidator(): ConditionValidator<Rental> = Validator(rentalDbConditions::fetchRentalSaveConditions)

    @Bean
    fun rentalSaveDateValidator(): ConditionValidator<Rental> = Validator(rentalDbConditions::fetchRentalDateConditions)

    @Bean
    fun rentalUpdateValidator(): ConditionValidator<Rental> = Validator(rentalDbConditions::fetchRentalUpdateConditions)
}