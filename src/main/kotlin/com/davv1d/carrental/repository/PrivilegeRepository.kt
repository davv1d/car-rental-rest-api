package com.davv1d.carrental.repository

import com.davv1d.carrental.domain.Privilege
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Transactional
@Repository
interface PrivilegeRepository : CrudRepository<Privilege, Int> {
}