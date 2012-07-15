package org.devnull.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

import org.springframework.security.core.userdetails.UsernameNotFoundException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.devnull.security.dao.UserDao

@Service("openIdUserDetailsService")
class OpenIdUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao

    UserDetails loadUserByUsername(String openIdToken) {
        def user = userDao.findByOpenId(openIdToken)
        if (user) return user
        throw new UsernameNotFoundException("Unable to locate user with open id token :${openIdToken}")
    }
}
