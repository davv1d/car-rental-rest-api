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
    fun existsByName(name: String): Boolean

    @Query(value = "select case when count(r) = 0 then true else false end from Role r where upper(r.name) like upper(:name)")
    fun doesRoleNotExistByName(name: String): Boolean

    @Query(value = "select case when count(r) = 0 then true else false end from Role r where upper(r.id) = :id")
    fun doesRoleNotExistById(id: Int): Boolean

    override fun findAll(): List<Role>

    @Query(nativeQuery = true,
            value = "select case when count(*) > 0 then 'true' else 'false' end from USERS as U where U.ROLE_ID like :id")
    fun areThereUsersWithThisRole(id: Int): Boolean

    @Query(nativeQuery = true,
            value = "select case when count(*) > 0 then true else false end from ROLES as r where " +
                    "((r.id = :id and upper(r.name) not like upper(:name)) or (select case when count(*) = 0 then 1 else 0 end from ROLES as r where r.id = :id )) and " +
                    "(select count(*) from ROLES as r where upper(r.name) like upper(:name))")
    fun doesNameExistOrNotBelongToTheUserWithThisId(name: String, id: Int): Boolean

}