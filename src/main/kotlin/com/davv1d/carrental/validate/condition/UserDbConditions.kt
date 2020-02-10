package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.constants.EMAIL_EXIST_IN_DATABASE
import com.davv1d.carrental.constants.ROLE_WITH_THIS_NAME_IS_NOT_EXIST
import com.davv1d.carrental.constants.USERNAME_EXIST_IN_DATABASE
import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.User
import com.davv1d.carrental.repository.RoleRepository
import com.davv1d.carrental.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserDbConditions(private val roleRepository: RoleRepository, private val userRepository: UserRepository) : ConditionGenerator<User> {

    override fun get(value: User): List<Condition<User>> {
        val con1 = Condition(value, USERNAME_EXIST_IN_DATABASE, { u -> userRepository.existsByUsername(u.username) })
        val con2 = Condition(value, EMAIL_EXIST_IN_DATABASE, { u -> userRepository.existsByEmail(u.email) })
        val con3 = Condition(value, ROLE_WITH_THIS_NAME_IS_NOT_EXIST, { u -> roleRepository.doesRoleNotExistByName(u.role.name) })
        return listOf(con1, con2, con3)
    }
}