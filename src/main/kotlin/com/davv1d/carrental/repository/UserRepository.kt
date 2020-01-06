package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface UserRepository : CrudRepository<User, Int> {
    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    override fun findAll(): List<User>
}