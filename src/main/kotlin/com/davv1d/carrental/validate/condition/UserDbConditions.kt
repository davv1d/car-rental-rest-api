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
class UserDbConditions(
        private val roleRepository: RoleRepository,
        private val userRepository: UserRepository) {

    fun fetchUserSaveConditions(): List<Condition<User>> {
        val con1 = Condition<User>(USERNAME_EXIST_IN_DATABASE) { u -> userRepository.existsByUsername(u.username) }
        val con2 = Condition<User>(EMAIL_EXIST_IN_DATABASE) { u -> userRepository.existsByEmail(u.email) }
        val con3 = Condition<User>(ROLE_WITH_THIS_NAME_IS_NOT_EXIST) { u -> roleRepository.doesRoleNotExistByName(u.role.name) }
        return listOf(con1, con2, con3)
    }
}