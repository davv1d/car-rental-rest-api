package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Role
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface RoleRepository : CrudRepository<Role, String> {
    fun findByName(name: String): Role?
    fun existsByName(name: String): Boolean

    @Query(value = "select case when count(r) = 0 then true else false end from Role r where r.name like :name")
    fun doesRoleNotExistByName(name: String): Boolean

    override fun findAll(): List<Role>
}