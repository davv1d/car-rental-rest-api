package com.davv1d.carrental.configuration

import com.davv1d.carrental.domain.Role
import com.davv1d.carrental.domain.User
import com.davv1d.carrental.validate.ConditionValidator
import com.davv1d.carrental.validate.Validator
import com.davv1d.carrental.validate.condition.ConditionGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ValidateConfig {
    @Autowired
    lateinit var userDbConditions: ConditionGenerator<User>
    @Autowired
    lateinit var roleDbConditions: ConditionGenerator<Role>

    @Bean
    fun userValidator(): ConditionValidator<User> {
        return Validator(userDbConditions)
    }

    @Bean
    fun roleDbValidator(): ConditionValidator<Role> {
        return Validator(roleDbConditions)
    }
}