package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Privilege
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Transactional
@Repository
interface PrivilegeRepository : CrudRepository<Privilege, Int> {
    fun existsByName(name: String): Boolean
    override fun findAll(): Set<Privilege>
    @Query(value = "select case when count(p) = 0 then true else false end from Privilege p where upper(p.name) like upper(:name)")
    fun doesNotExistByName(name: String): Boolean

    @Query(value = "select p from Privilege p where p.name in (:names)")
    fun getMany(names: Set<String>): Set<Privilege>

    fun deleteByName(name: String)
}