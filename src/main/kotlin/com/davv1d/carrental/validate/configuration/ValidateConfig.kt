package com.davv1d.carrental.validate.configuration

import com.davv1d.carrental.domain.Location
import com.davv1d.carrental.domain.Privilege
import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.User
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.Validator
import com.davv1d.carrental.validate.condition.ConditionGenerator
import com.davv1d.carrental.validate.condition.LocationDbConditions
import com.davv1d.carrental.validate.condition.PrivilegeDbConditions
import com.davv1d.carrental.validate.condition.UpdateRoleConditions
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

    @Bean
    fun userValidator(): ConditionValidator<User> {
        return Validator(userDbConditions)
    }

    @Bean
    fun roleDbValidator(): ConditionValidator<Role> {
        return Validator(roleDbConditions)
    }

    @Bean
    fun locationDbValidator(): ConditionValidator<Location> {
        return Validator(locationDbConditions)
    }

    @Bean
    fun privilegeDbValidator(): ConditionValidator<Privilege> {
        return Validator(privilegeDbConditions)
    }

    @Bean
    fun updateRoleValidator(): ConditionValidator<Role> {
        return Validator(updateRoleConditions)
    }
}