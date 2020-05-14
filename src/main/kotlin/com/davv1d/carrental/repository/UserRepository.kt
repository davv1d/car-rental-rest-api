package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
interface UserRepository : CrudRepository<User, Int> {
    @Query(value = "select case when count(u) > 0 then true else false end from User u where upper(u.username) like upper(:username) and u.active = true")
    fun existsByUsernameAmongActiveUsers(username: String): Boolean

    @Query(value = "select case when count(u) = 0 then true else false end from User u where upper(u.email) like upper(:email)")
    fun doesEmailNotExist(email: String): Boolean

    @Query(value = "select case when count(u) = 0 then true else false end from User u where upper(u.username) like upper(:username)")
    fun doesUsernameNotExist(username: String): Boolean

    fun findByUsername(username: String): Optional<User>

    override fun findAll(): List<User>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Optional<User>
    fun deleteByUsername(username: String)
}