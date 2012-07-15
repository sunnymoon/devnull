package org.devnull.security.dao

import org.springframework.data.repository.PagingAndSortingRepository

import org.devnull.security.model.User

interface UserDao extends PagingAndSortingRepository<User, Long> {
    User findByOpenId(String openId)
}