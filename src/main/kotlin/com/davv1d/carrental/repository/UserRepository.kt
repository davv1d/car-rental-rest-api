package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
interface UserRepository : CrudRepository<User, Int> {
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
    override fun findAll(): List<User>
    fun deleteByUsername(username: String)
}