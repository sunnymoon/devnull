package org.devnull.security.dao

import org.devnull.security.model.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.jpa.repository.Modifying

interface UserDao extends PagingAndSortingRepository<User, Integer> {
    User findByUserName(String userName)

    @Modifying
    User save(User entity)


}