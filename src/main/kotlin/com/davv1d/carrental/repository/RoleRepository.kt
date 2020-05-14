package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Role
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*
import javax.transaction.Transactional

@Repository
@Transactional
interface RoleRepository : CrudRepository<Role, Int> {
    override fun findById(id: Int): Optional<Role>
    fun findByName(name: String): Optional<Role>

    @Query(value = "select case when count(r) > 0 then true else false end from Role r where upper(r.name) like upper(:name)")
    fun doesRoleExistByName(name: String): Boolean

    @Query(value = "select case when count(r) = 0 then true else false end from Role r where upper(r.name) like upper(:name)")
    fun doesRoleNotExistByName(name: String): Boolean

    override fun findAll(): List<Role>

    @Query(value = "select case when count(u) = 0 then true else false end from User as u where u.role.id = :roleId")
    fun doUsersNotHaveRoleWithThisId(roleId: Int): Boolean

    @Query(nativeQuery = true,
            value = "select case when count(*) > 0 then true else false end from ROLES as r where " +
                    "(r.id = :id and upper(r.name) like upper(:name)) or " +
                    "(select case when count(*) = 0 then 1 else 0 end from ROLES as r where upper(r.name) like upper(:name))")
    fun doesNameNotExistOrBelongToTheUserWithThisId(name: String, id: Int): Boolean
}