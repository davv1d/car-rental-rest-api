package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Role
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
@Transactional
interface RoleRepository : CrudRepository<Role, Int> {
    fun findByName(name: String): Role?
    fun existsByName(name: String): Boolean

    @Query(value = "select case when count(r) = 0 then true else false end from Role r where upper(r.name) like upper(:name)")
    fun doesRoleNotExistByName(name: String): Boolean

    override fun findAll(): List<Role>

    @Query(nativeQuery = true,
            value = "select case when count(*) > 0 then 'true' else 'false' end from USERS as U where U.ROLE_ID like :id")
    fun areThereUsersWithThisRole(id: Int): Boolean

}