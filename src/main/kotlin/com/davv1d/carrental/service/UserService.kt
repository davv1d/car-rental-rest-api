package com.davv1d.carrental.service

import com.davv1d.carrental.constants.AdminAccountConfig
import com.davv1d.carrental.constants.EMAIL_EXIST_IN_DATABASE
import com.davv1d.carrental.constants.USER_WITH_THIS_EMAIL_IS_NOT_EXIST
import com.davv1d.carrental.constants.USER_WITH_THIS_NAME_IS_NOT_EXIST
import com.davv1d.carrental.domain.Role
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
        return userValidator.dbValidate(user)
                .flatMap { convert(it, roleService::getRoleByName) }
                .flatMap { this.secureSave(it) }
    }

    fun convert(u: User, getRole: (String) -> Result<Role>): Result<User> {
        return getRole.invoke(u.role.name)
                .map { role -> User(username = u.username, password = u.password, email = u.email, role = role, active = u.active) }
    }
    fun changeEmail(username: String, email: String): Result<User> =
            getUserByName(username)
                    .filter(EMAIL_EXIST_IN_DATABASE) { user -> !userRepository.existsByEmail(user.email) }
                    .map { User(it.id, it.username, it.password, email, it.role, it.active) }


    fun secureSave(user: User): Result<User> = generalService.secureSave(user, userRepository::save)

    fun deleteUserByUsername(username: String): Result<Unit> {
        return getUserByName(username).map { userRepository.deleteByUsername(it.username) }
    }

    fun disableAccount(name: String): Result<User> {
        return when (AdminAccountConfig.name != name) {
            true -> changeAccountActivity(name, false)
            else -> Result.failure("CANNOT DISABLE ADMIN ACCOUNT")
        }
    }

    fun enableAccount(name: String): Result<User> = changeAccountActivity(name, true)

    fun changeAccountActivity(name: String, activity: Boolean): Result<User> {
        return getUserByName(name)
                .map { user -> with(user) { User(id, username, password, email, role, activity) } }
                .flatMap(::secureSave)
    }
}