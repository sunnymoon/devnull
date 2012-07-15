package org.devnull.security.dao

import org.devnull.security.model.Role
import org.springframework.data.repository.PagingAndSortingRepository

interface RoleDao extends PagingAndSortingRepository<Role, Integer>  {

}