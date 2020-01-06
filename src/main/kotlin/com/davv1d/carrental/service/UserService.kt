package com.davv1d.carrental.service

import com.davv1d.carrental.constants.EMAIL_EXIST_IN_DATABASE
import com.davv1d.carrental.constants.USER_WITH_THIS_EMAIL_IS_NOT_EXIST
import com.davv1d.carrental.constants.USER_WITH_THIS_NAME_IS_NOT_EXIST
import com.davv1d.carrental.domain.User
import com.davv1d.carrental.error.NotFoundElementException
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.repository.UserRepository
import com.davv1d.carrental.validate.ConditionValidator
import org.springframework.stereotype.Service

@Service
class UserService(
        private val roleService: RoleService,
        private val userRepository: UserRepository,
        private val userValidator: ConditionValidator<User>,
        private val generalService: GeneralService) {


    fun getUserByName(username: String): Result<User> = generalService.getByValue(
            value = username,
            error = NotFoundElementException(USER_WITH_THIS_NAME_IS_NOT_EXIST),
            function = userRepository::findByUsername)

    fun getUserByEmail(email: String): Result<User> = generalService.getByValue(
            value = email,
            error = NotFoundElementException(USER_WITH_THIS_EMAIL_IS_NOT_EXIST),
            function = userRepository::findByEmail)

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun save(user: User): Result<User> {
        return with(user) {
            userValidator.dbValidate(this)
                    .flatMap {
                        roleService.getRoleByName(user.role.name)
                                .map { role -> User(username = user.username, password = user.password, email = user.email, role = role) }
                                .flatMap { this@UserService.secureSave(it) }
                    }
        }
    }

    fun changeEmail(username: String, email: String): Result<User> =
            getUserByName(username)
                    .filter(EMAIL_EXIST_IN_DATABASE) { user -> !userRepository.existsByEmail(user.email) }
                    .map { User(it.id, it.username, it.password, email, it.role) }


    fun secureSave(user: User): Result<User> = generalService.secureSave(user, userRepository::save)
}