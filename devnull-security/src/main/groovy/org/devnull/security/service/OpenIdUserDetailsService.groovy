package org.devnull.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

import org.springframework.security.core.userdetails.UsernameNotFoundException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.devnull.security.dao.UserDao
import org.slf4j.LoggerFactory

@Service("openIdUserDetailsService")
class OpenIdUserDetailsService implements UserDetailsService {

    final def log = LoggerFactory.getLogger(this.class)

    @Autowired
    UserDao userDao

    UserDetails loadUserByUsername(String openIdToken) {
        log.debug("Attempting to load user by token {}", openIdToken)
        def user = userDao.findByOpenId(openIdToken)
        log.debug("User={}", user)
        if (user) return user
        throw new UsernameNotFoundException("Unable to locate user with open id token :${openIdToken}")
    }
}
